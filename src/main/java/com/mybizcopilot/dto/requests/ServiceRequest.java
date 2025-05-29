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

    @NotNull(message = "Nous n'avons pas pu trouver vos informations")
    private Integer idUser;

    @NotNull(message = "L'entreprise n'est pas reconnue")
    private Integer idEntreprise;

    private String description;

    @NotEmpty(message = "Veuillez entrer un libellé pour le service")
    private String libelle;

    private Integer dureeInitiale;

    private Double prixInitial;

    private Integer qteInitiale;

}
