package com.mybizcopilot.dto.requests;

import com.mybizcopilot.exception.OperationNonPermittedException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class ServiceRequest {

    @NotNull
    private Integer idUser;

    @NotNull(message = "L'entreprise n'est pas reconnue")
    private Integer idEntreprise;

    @NotEmpty(message = "Veuillez entrer une description du service")
    private String description;

    @NotEmpty(message = "Veuillez entrer un nom pour le service")
    private String libelle;

    private Integer dureeInitiale;

    @NotNull(message = "Entrez le prix initial pour ce service")
    private Double prixInitial;

    private Integer qteInitiale;

    private void checkTypeService(){
        if (dureeInitiale == null && qteInitiale == null)
            throw new OperationNonPermittedException("Vous devez renseignez soit la durée de réalisation, soit la quantité");
    }
}
