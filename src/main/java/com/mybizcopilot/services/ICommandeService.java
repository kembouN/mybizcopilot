package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.CommandeRequest;
import com.mybizcopilot.dto.responses.CommandeResponse;

import java.util.List;

public interface ICommandeService {

    Void ajouterCommande(CommandeRequest request);

    CommandeResponse afficherCommande(Integer idCommande);

    List<CommandeResponse> listeCommandes(Integer idEntreprise);

    Void updateCommande(Integer idCommande, CommandeRequest request);

    Void affecterCommande(Integer idCommande, Integer idCollaborateur);

    Void terminerCommande(Integer idCommande);

}
