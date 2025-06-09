package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;

    private String codeEntreprise;

    private String nomEntreprise;

    private String emailEntreprise;

    private String telephone1Entreprise;

    private String telephone2Entreprise;

    private String ville;

    private String adresse;

    private String descriptionEntreprise;

    private byte[] logoEntreprise;

    @OneToMany(mappedBy = "entreprise")
    private List<Service> services;

    @ColumnDefault("1")
    private Integer isActive;

}
