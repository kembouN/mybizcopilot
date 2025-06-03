package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.CollaborateurRequest;
import com.mybizcopilot.dto.responses.CollaborateurResponse;

import java.util.List;

public interface ICollaborateurService {

    Void ajouterCollaborateur(CollaborateurRequest request);

    List<CollaborateurResponse> getAllCollaborateurs(Integer idEntreprise);

    Void updateCollaborateur(Integer idCollaborateur, CollaborateurRequest request);

}
