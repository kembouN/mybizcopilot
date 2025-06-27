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

    @NotEmpty(message = "Veuillez entrer votre mot de passe")
    private String password;

    @NotEmpty(message = "Veuillez entrer le nouveau mot de passe")
    @Size(min = 8, max = 20, message = "Le mot de passe doit comprendre entre 8 et 20 caractères")
    private String newPassword;

    @NotEmpty(message = "Veuillez entrer la confimation du mot de passe")
    private String cPassword;

    public void checkPasswordConfirmation(){
        if (!newPassword.equals(cPassword))
            throw new IllegalArgumentException("La confirmation du mot de passe est incorrecte");
    }
}
