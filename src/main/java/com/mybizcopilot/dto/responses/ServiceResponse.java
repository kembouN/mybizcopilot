package com.mybizcopilot.dto.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
public class ServiceResponse {

    private Integer idService;

    private String description;

    private String libelle;

    private Integer dureeInitiale;

    private Double prixInitial;

    private Integer qteInitiale;
}
