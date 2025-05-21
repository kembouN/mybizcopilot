package com.mybizcopilot.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class LoginRequest {

    @NotEmpty(message = "Veuillez entrer votre nom d'utilisateur")
    private String username;

    @NotEmpty(message = "Veuillez entrer votre mot de passe")
    private String pass;
}
