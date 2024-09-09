package spring.domain.entities.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class LoginResponseDTO {
    private String status;
    private String jwtToken;
}
