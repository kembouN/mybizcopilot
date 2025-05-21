package com.mybizcopilot.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ChangePasswordRequest {

    private String password;

    @NotEmpty(message = "Veuilez entrer un mot de passe")
    @Size(min = 8, max = 20, message = "Le mot de passe doit comprendre entre 8 et 20 caractères")
    private String newPassword;

    private String cPassword;
}
