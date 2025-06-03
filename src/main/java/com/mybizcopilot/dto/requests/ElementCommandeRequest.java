package com.mybizcopilot.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ElementCommandeRequest {
    @NotNull(message = "Veuillez sélectionner un sous-service pour chaque demande")
    private Integer idSousservice;

    @NotNull(message = "Veuillez entrer une quantité pour chaque demande")
    private Integer quantite;

    @NotNull(message = "Le prix est obligatoire pour chaque élément")
    private Double prix;
}
