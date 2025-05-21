package com.mybizcopilot.dto.responses;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

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
}
