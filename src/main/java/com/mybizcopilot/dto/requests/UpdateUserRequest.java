package com.mybizcopilot.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class UpdateUserRequest {

    private String nom;

    @NotEmpty(message = "Veuillez entrer votre email")
    @Email(message = "Votre email est incorrect")
    private String email;
}
