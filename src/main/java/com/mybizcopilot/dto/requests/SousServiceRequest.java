package com.mybizcopilot.dto.requests;

import com.mybizcopilot.entities.UniteTemps;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class SousServiceRequest {

    @NotNull(message = "Veuillez choisir un service pour le sous-service")
    private Integer idService;

    @NotEmpty(message = "Entrez un libelle pour le sous-service")
    private String libelle;

    private String description;

    private Integer duree;

    @Enumerated(EnumType.STRING)
    private UniteTemps unite;
}
