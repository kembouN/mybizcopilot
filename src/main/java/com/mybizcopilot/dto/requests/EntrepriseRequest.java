package com.mybizcopilot.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class EntrepriseRequest {

    @NotNull(message = "Le responsable de l'entreprise est introuvable")
    private Integer idUser;

    @NotEmpty(message = "Ajouter une description de votre entreprise")
    private String description;

    @NotEmpty(message = "Renseignez une adresse e-mail pour votre entreprise")
    @Email
    private String email;


    private String localisation;

    @NotEmpty(message = "Veuillez renseigner le nom de votre entreprise")
    private String nom;

    @NotEmpty(message = "Vous devez rensiigner au moins un numéro de téléphone")
    private String telephone1;

    private String telephone2;

    private MultipartFile logo;
}
