package com.mybizcopilot.dto.responses;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
public class CommandeResponse {

    private Integer idCommande;

    private String client;

    private String service;

    private LocalDate dateCommande;

    private LocalDate dateDebut;

    private Integer qte;

    private Integer duree;

    private LocalDate dateFin;

    private Double cout;

    private Double avance;

    private LocalDate dateAvance;

    private Integer paye;

    private LocalDate datePaiement;

    private String statutCommande;
}
