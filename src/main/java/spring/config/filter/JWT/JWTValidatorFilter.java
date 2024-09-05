package spring.config.filter.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.constants.ApplicationConstants;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTValidatorFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader(ApplicationConstants.JWT_HEADER);
            Authentication authentication = jwtService.getPayloadByTokenHeader(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exception) {
            throw new BadCredentialsException("Invalid Token received!");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorizationHeader = request.getHeader(ApplicationConstants.JWT_HEADER);
        return authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
    }
}
