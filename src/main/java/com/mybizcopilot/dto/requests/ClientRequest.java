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

    @NotNull
    private Integer idUser;

    //@NotNull(message = "Veuillez sélectionner le service désiré par le client")
    private Integer idService;

    //@NotNull(message = "l'entreprise correspondante n'est pas renseigné")
    private Integer idEntreprise;

    @NotEmpty(message = "Veuillez entrer au moins une adresse e-mail")
    @Email(message = "Le format du premier email est incorrect")
    private String emailUn;

    @Email(message = "Le format du deuxième email est incorrect")
    private String emailDeux;

    @NotEmpty(message = "Veuillez entrer le nom du client")
    private String nom;

    private boolean isClient;

    @NotEmpty(message = "Veuillez entrer au moins un numéro de téléphone")
    private String telephoneUn;

    private String telephoneDeux;

    private Integer idTranche;

    private Integer idTypeprospect;


    public void checkTypeProspect(){
        if (!isClient && idTypeprospect == null) throw new OperationNonPermittedException("Veuillez chosir un type de prospect correspondant");
    }
}
