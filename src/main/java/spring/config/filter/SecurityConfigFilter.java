package spring.config.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import spring.config.security.exceptionHandling.CustomBasicAuthenticationEntryPoint;
import spring.config.security.exceptionHandling.CustomHandlerAccessDeniedHandler;
import spring.config.security.provider.CustomProviderAuthentication;
import spring.config.filter.JWT.JWTTokenGeneratorFilter;
import spring.config.filter.JWT.JWTValidatorFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class SecurityConfigFilter {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,JWTTokenGeneratorFilter jwtTokenGeneratorFilter, JWTValidatorFilter jwtValidatorFilter) throws Exception {
        http.sessionManagement((smc-> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/user/apiLogin").permitAll()
                .requestMatchers("/wellcome").permitAll()
                .anyRequest().authenticated());
        http.formLogin(withDefaults());
//        http.httpBasic(hbs -> hbs.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc-> ehc.accessDeniedHandler(new CustomHandlerAccessDeniedHandler()).authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.anonymous(AbstractHttpConfigurer::disable);
        http.addFilterAfter(jwtTokenGeneratorFilter, BasicAuthenticationFilter.class);
        http.addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter.class);
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
