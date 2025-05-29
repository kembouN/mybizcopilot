package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "pays")
public class Pays extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPays;

    private String abreviationPays;

    private String codePays;

    private String libellePays;

}
