package spring.config.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import spring.config.security.exceptionHandling.CustomBasicAuthenticationEntryPoint;
import spring.config.security.exceptionHandling.CustomHandlerAccessDeniedHandler;
//import spring.config.security.exceptionHandling.CustomBasicAuthenticationEntryPoint;
//import spring.config.security.exceptionHandling.CustomHandlerAccessDeniedHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class SecurityConfigFilterProd {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement((smc-> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS).maximumSessions(5).maxSessionsPreventsLogin(true)))
                .requiresChannel(rcc-> rcc.anyRequest().requiresInsecure())
                .csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/user/register").authenticated()
                .anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(hbs -> hbs.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc-> ehc.accessDeniedHandler(new CustomHandlerAccessDeniedHandler()));
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
}
