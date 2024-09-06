package spring.config.filter.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.constants.ApplicationConstants;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTService {

    private final Environment env;

    public String generateJWT(Authentication authentication) {
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
        return token;
    }

    public UsernamePasswordAuthenticationToken  getPayloadByTokenHeader(String jwt) {
        if (jwt.startsWith("Bearer ")) {
            try{
                String token = jwt.substring(7);
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser().verifyWith(secretKey)
                        .build().parseSignedClaims(token).getPayload();
                String username = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("role"));
                UsernamePasswordAuthenticationToken  authentication = new UsernamePasswordAuthenticationToken (username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                return  authentication;
            }catch (RuntimeException exception){
                throw new RuntimeException("Erro ao gerar token JWT", exception);
            }
        }
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(jwt).getPayload();
        String username = String.valueOf(claims.get("username"));
        String authorities = String.valueOf(claims.get("authorities"));
        UsernamePasswordAuthenticationToken  authentication = new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
        return  authentication;
    }
}
