package com.mybizcopilot.dto.responses;

import com.mybizcopilot.entities.Pays;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Data
@Builder
public class ClientResponse {

    private Integer idClient;

    private String code;

    private String emailUn;

    private String emailDeux;

    private String telephoneUn;

    private String telephoneDeux;

    private String nomClient;

    private String statut;

    private String tranche;

    private Integer trancheId;

    private Integer typeprospectId;

    private String ville;

    private String adresse;

    private String agentLiaison;

    private String typeClient;

    private Pays pays;

}
