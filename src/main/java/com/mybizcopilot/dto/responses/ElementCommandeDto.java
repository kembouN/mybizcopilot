package com.mybizcopilot.dto.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
public class ElementCommandeDto {

    private Integer idElement;

    private Integer idSousservice;

    private Integer quantite;

    private Double prix;
}
