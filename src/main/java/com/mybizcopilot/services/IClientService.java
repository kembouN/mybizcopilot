package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.ClientRequest;
import com.mybizcopilot.dto.responses.ClientResponse;
import com.mybizcopilot.entities.Pays;

import java.util.List;

public interface IClientService {

    Void ajouterClient(ClientRequest request);

    ClientResponse getClient(Integer idClient);

    List<ClientResponse> getAllClient(Integer idEntreprise, String nom, String typeClient, String localisation, Integer typeProspect);

    ClientResponse updateClient(Integer idClient, ClientRequest request);

    List<ClientResponse> getAllClients();

    List<Pays> getAllPays();
}
