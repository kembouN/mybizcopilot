package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "typeprospect")
@Builder
@Getter
@Setter
public class Typeprospect extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTypeprospect;

    private String libelleTypeprospect;
}
