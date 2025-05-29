package com.mybizcopilot.dto.requests;

import com.mybizcopilot.exception.OperationNonPermittedException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
public class ClientRequest {

    @NotNull(message = "Nous n'avons pas pu trouver vos informations")
    private Integer idUser;

    @NotNull(message = "l'entreprise n'est pas reconnue")
    private Integer idEntreprise;

    @Email(message = "Le format du premier email est incorrect")
    private String emailUn;

    @Email(message = "Le format du deuxième email est incorrect")
    private String emailDeux;

    @NotEmpty(message = "Veuillez entrer le nom du client")
    private String nom;

    @NotEmpty(message = "A quel type appartient le client?")
    private String typeClient;

    private String agentLiaison;

    @NotNull(message = "S'agit-il d'un client ou d'un prospect")
    private Integer isClient;

    private String telephoneUn;

    @NotEmpty(message = "Veuillez entrer au moins un numéro de téléphone")
    private String telephoneDeux;

    private Integer idTranche;

    @NotNull(message = "Veuillez renseigner le pays du client")
    private Integer paysId;

    private String ville;

    private String adresse;

    private Integer idTypeprospect;


    public void checkTypeProspect(){
        if (isClient == 0 && idTypeprospect == null) throw new OperationNonPermittedException("Veuillez choisir un type de prospect correspondant");

        if (typeClient.equals("PERSONNE MORALE") && agentLiaison == null) throw new OperationNonPermittedException("Veuillez renseigner le nom de l'agent de liaison pour ce type de client");
    }
}
