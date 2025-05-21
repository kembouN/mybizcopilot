package com.mybizcopilot.services;


import com.mybizcopilot.dto.requests.EntrepriseRequest;
import com.mybizcopilot.dto.responses.EntrepriseResponse;
import com.mybizcopilot.entities.Entreprise;

import java.util.List;

public interface IEntrepriseService {

    Void ajouterEntreprise(EntrepriseRequest request);

    EntrepriseResponse getEntreprise(Integer idEntreprise);

    List<EntrepriseResponse> getAllEntrepriseByUser(Integer idUser);

    Entreprise updateEntreprise(Integer idEnterprise, EntrepriseRequest request);


}
