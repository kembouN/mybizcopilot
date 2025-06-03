package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
@Builder
@Getter
@Setter
public class Service extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idService;

    @ManyToOne
    @JoinColumn(name = "id_entreprise")
    private Entreprise entreprise;

    @Column(unique = true)
    private String libelleService;

    private String descriptionService;

    private Integer quantiteInitiale;

    private Integer dureeInitiale;

    private Double prixInitial;

    @ColumnDefault("1")
    private Integer isActive;
}
