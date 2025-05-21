package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commandes")
@Builder
@Getter
@Setter
public class Commande extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCommande;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service service;

    private LocalDate dateContact;

    private LocalDate dateDebut;

    private Integer quantite;

    private Integer duree;

    private LocalDate dateFin;

    private Double cout;

    private Double avanceCout;

    private LocalDate dateAvance;

    private Integer paye;

    private LocalDate datePaiement;

    private String statutCommande;


}
