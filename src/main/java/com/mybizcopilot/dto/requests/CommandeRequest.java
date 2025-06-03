package com.mybizcopilot.dto.requests;

import com.mybizcopilot.dto.responses.ElementCommandeDto;
import com.mybizcopilot.exception.OperationNonPermittedException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class CommandeRequest {

    @NotNull(message = "Veuillez choisir un client")
    private Integer idClient;

    private Double avance;

    private LocalDate dateAvance;

    @NotNull(message = "A quelle date le client a t-il passé sa commande?")
    private LocalDate dateContact;

    private Integer paye;

    private LocalDate datePaiement;

    @NotNull(message = "Veuillez estimer une date de livraison")
    private LocalDate dateFin;

    @NotNull(message = "Vous devez entrer au moins un élément commandé")
    @NotEmpty(message = "Vous devez entrer au moins un élément commandé")
    private List<ElementCommandeDto> items;

    public void checkSomeField(){
        if (paye == 1 && datePaiement == null)
            throw new OperationNonPermittedException("Veuillez renseigner à quelle date le service a été payé");

        if ((avance != 0  ) && dateAvance == null)
            throw new OperationNonPermittedException("Veuillez renseigner la date de l'avancement");
    }
}
