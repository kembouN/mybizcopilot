package com.mybizcopilot.dto.responses;

import com.mybizcopilot.entities.Pays;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
public class CollaborateurResponse {

    private Integer idCollaborateur;

    private Pays pays;

    private String nom;

    private String telephoneUn;

    private String telephoneDeux;

    private String ville;

    private int tache;

    private String adresse;

}
