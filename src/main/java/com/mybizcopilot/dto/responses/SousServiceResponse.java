package com.mybizcopilot.dto.responses;

import com.mybizcopilot.entities.UniteTemps;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
public class SousServiceResponse {

    private Integer idSousService;

    private String libelle;

    private String description;

    private Integer idService;

    private String libelleService;

    private Integer duree;

    private UniteTemps unite;

}
