package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private String libelle;

    private String description;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service service;

    private Integer duree;

    private char uniteDuree;
}
