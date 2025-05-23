package com.mybizcopilot.dto.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class EntrepriseResponse {

    private Integer entrepriseId;

    private String description;

    private String email;

    private String pays;

    private String ville;

    private byte[] logo;

    private String nom;

    private String telephone1;

    private String telephone2;

    private String code;

    private String responsable;

}
