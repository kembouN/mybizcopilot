package com.mybizcopilot.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class RegisterRequest {

    private String username;

    @NotEmpty(message = "Veuillez entrer votre adresse e-mail")
    private String email;

    @NotEmpty(message = "Veuilez entrer un mot de passe")
    @Size(min = 8, max = 20, message = "Le mot de passe doit comprendre entre 8 et 20 caractères")
    private String pass;

    @NotEmpty(message = "Entrez une confirmation du mot de passe")
    private String cPass;

}
