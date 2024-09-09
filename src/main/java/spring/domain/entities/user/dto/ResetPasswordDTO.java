package spring.domain.entities.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ResetPasswordDTO {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
