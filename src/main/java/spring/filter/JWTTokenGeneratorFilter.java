package spring.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.constants.ApplicationConstants;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Environment env = getEnvironment();
            String secret = env.getProperty(ApplicationConstants.JWT_SECRET, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            String token = Jwts
                    .builder()
                    .issuer("teste")
                    .subject("JWT Token")
                    .claim("email", authentication.getName())
                    .claim("role", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                    .signWith(secretKey).compact();
            response.addHeader(ApplicationConstants.JWT_HEADER, token);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/info");
    }
}
