package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "id_typeprospect", nullable = false)
    private Typeprospect typeProspect;

    private String nomClient;

    private String telephoneUn;

    private String telephoneDeux;

    private String emailUn;

    private String emailDeux;

    private Integer isClient;

}
