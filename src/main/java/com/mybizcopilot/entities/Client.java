package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Client extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;

    @ManyToOne
    @JoinColumn(name = "id_tranche")
    private Tranche tranche;

    @Column(unique = true, updatable = false)
    private String codeClient;

    @ManyToOne
    @JoinColumn(name = "id_typeprospect")
    private Typeprospect typeProspect;

    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;

    @ManyToOne
    @JoinColumn(name = "id_entreprise")
    private Entreprise entreprise;

    @Column(nullable = false)
    private String typeClient;

    @ColumnDefault("0")
    private Integer tel1IsWhatsapp;

    private String nomClient;

    private String telephoneUn;

    private String telephoneDeux;

    private String agentLiaison;

    private String emailUn;

    private String emailDeux;

    private Integer isClient;

    private String ville;

    private String adresse;

}
