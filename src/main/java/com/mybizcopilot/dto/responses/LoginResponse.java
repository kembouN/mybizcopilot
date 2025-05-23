package com.mybizcopilot.dto.responses;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class LoginResponse {

    private String username;

    private String email;

    private LocalDate lastconnexion;

    private Integer idUser;

    private String token;

    List<EntrepriseResponse> entreprises;
}
