package com.mybizcopilot.dto.responses;

import com.mybizcopilot.entities.StatutCommande;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
public class CommandeResponse {
    private Integer idClient;

    private Integer idCommande;

    private String client;

    private LocalDate dateCommande;

    private LocalDate dateFin;

    private Double cout;

    private Double avance;

    private LocalDate dateAvance;

    private Integer paye;

    private LocalDate datePaiement;

    private StatutCommande statutCommande;

    private List<ElementCommandeDto> elements;
}
