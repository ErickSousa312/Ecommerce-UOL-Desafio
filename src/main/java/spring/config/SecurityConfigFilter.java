package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import spring.exceptionHandling.CustomBasicAuthenticationEntryPoint;
import spring.exceptionHandling.CustomHandlerAccessDeniedHandler;
import spring.filter.JWTTokenGeneratorFilter;
import spring.filter.JWTValidatorFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class SecurityConfigFilter {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement((smc-> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .requiresChannel(rcc-> rcc.anyRequest().requiresInsecure())
                .csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/user/register").authenticated()
                .requestMatchers("/info/**").permitAll()
                .requestMatchers("/user/apiLogin/**").permitAll()
                .anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(hbs -> hbs.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc-> ehc.accessDeniedHandler(new CustomHandlerAccessDeniedHandler()));
        http.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JWTValidatorFilter(),BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        CustomProviderAuthentication customAuthentication = new CustomProviderAuthentication(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(customAuthentication);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
