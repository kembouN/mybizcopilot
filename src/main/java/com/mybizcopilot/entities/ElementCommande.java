package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "elements_commande")
@Builder
@Getter
@Setter
public class ElementCommande extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idElementcommande;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_sousservice")
    private SousService sousService;

    private Integer quantite;

    private Double prix;

}
