package com.mybizcopilot.dto.requests;

import com.mybizcopilot.exception.OperationNonPermittedException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
public class CollaborateurRequest {


    @NotNull(message = "Entreprise non renseignée")
    private Integer idEntreprise;

    @NotNull(message = "Veuillez choir un pays")
    private Integer idPays;

    @NotEmpty(message = "Veuillez entrer un nom pour le collaborateur")
    private String nomCollaborateur;

    //@NotEmpty(message = "Veuillez renseigner au moins un numéro de téléphone")
    private String telephoneUn;

    private String telephoneDeux;

    private String ville;

    private String adresse;

    public void checkNumbers(){
        if (telephoneDeux == null || telephoneDeux == null ) {
            throw new OperationNonPermittedException("Veuillez renseigner au moins un numéro de téléphone");
        }
    }
}
