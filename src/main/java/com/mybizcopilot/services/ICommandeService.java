package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.CommandeRequest;
import com.mybizcopilot.dto.responses.CommandeResponse;

import java.util.List;

public interface ICommandeService {

    Void ajouterCommande(CommandeRequest request);

    CommandeResponse afficherCommande(Integer idCommande);

    List<CommandeResponse> listeCommandes(Integer idEntreprise);

    CommandeResponse updateCommande(Integer idCommande, CommandeRequest request);
}
