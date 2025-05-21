package com.mybizcopilot.dto.responses;

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
}
