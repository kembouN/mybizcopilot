package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "commande")
    private List<ElementCommande> elementsCommande;

    @ManyToOne
    @JoinColumn(name = "id_collaborateur")
    private Collaborateur collaborateur;

    private LocalDate dateContact;

    private LocalDate dateFin;

    private Double cout;

    private Double avanceCout;

    private LocalDate dateAvance;

    private Integer paye;

    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private StatutCommande statutCommande;

}
