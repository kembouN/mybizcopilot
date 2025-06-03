package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sousservice")
@Builder
@Getter
@Setter
public class SousService extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSousservice;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service service;

    private String libelle;

    private String description;

    private Integer duree;

    @Enumerated(EnumType.STRING)
    private UniteTemps uniteDuree;

    @ColumnDefault("1")
    private Integer isActive;

}
