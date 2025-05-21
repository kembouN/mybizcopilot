package com.mybizcopilot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "utilisateurs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUtilisateur;

    private String nomUtilisateur;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String motpasse;

    private LocalDate derniereconnexion;

    @ColumnDefault("1")
    private Integer activeUtilisateur; //TODO implementer l'activation du compte pour une meilleure sécurité
}
