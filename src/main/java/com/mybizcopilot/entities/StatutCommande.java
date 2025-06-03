package com.mybizcopilot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatutCommande{

    EN_ATTENTE("EN ATTENTE"),
    EN_COURS("EN COURS"),
    TERMINEE("TERMINEE"),
    ANNULEE("ANNULEE"),
    LIVREE("LIVREE");

    private String libelle;
}
