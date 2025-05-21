package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entreprises")
@Builder
@Getter
@Setter
public class Entreprise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntreprise;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", unique = true)
    private Utilisateur utilisateur;

    private String codeEntreprise;

    private String nomEntreprise;

    private String emailEntreprise;

    private String telephone1Entreprise;

    private String telephone2Entreprise;

    private String localisation;

    private String descriptionEntreprise;

    private byte[] logoEntreprise;

    @OneToMany(mappedBy = "entreprise")
    private List<Service> services;

}
