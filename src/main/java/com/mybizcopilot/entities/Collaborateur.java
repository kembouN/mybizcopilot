package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collaborateurs")
@Builder
@Getter
@Setter
public class Collaborateur extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCollaborateur;

    @ManyToOne
    @JoinColumn(name = "id_entreprise")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;

    private String nom;

    private String telephoneUn;

    private String telephoneDeux;

    private String ville;

    private String adresse;



}
