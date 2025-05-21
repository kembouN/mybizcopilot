package com.mybizcopilot.dto.requests;

import com.mybizcopilot.exception.OperationNonPermittedException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class CommandeRequest {

    @NotNull(message = "Veuillez sélectionner un service désiré par le client")
    private Integer idService;

    @NotNull(message = "Veuillez choisir un client")
    private Integer idClient;


    private Double avance;

    private LocalDate dateAvance;

    @NotNull(message = "A quelle date le client a t-il passé sa commande?")
    private LocalDate dateContact;

    @NotNull(message = "Entrez une date de début pour cette commande")
    private LocalDate dateDebut;

    private Integer qte;

    private Integer duree;

    private boolean paye;

    private LocalDate datePaiement;

    @NotEmpty(message = "Veuillez renseignez le statut de la commande")
    private String statut;

    private LocalDate dateFin;

    public void checkSomeField(){
        if ((qte != null && duree != null) || (qte == null && duree == null)){
            throw new OperationNonPermittedException("Renseignez soit la quantité si le service est évalué en quantité, soit la durée");
        }
        if (paye && datePaiement == null)
            throw new OperationNonPermittedException("Veuillez renseigner à quelle date le service a été payé");
        if (avance != 0 && dateAvance == null)
            throw new OperationNonPermittedException("Veuillez renseigner la date de l'avancement");

    }
}
