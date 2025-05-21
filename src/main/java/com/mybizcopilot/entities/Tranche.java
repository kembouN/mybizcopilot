package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tranches")
@Getter
@Setter
public class Tranche extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTranche;

    private Integer ageMinimal;

    private Integer agemaximal;

    private String libelleTranche;
}
