package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.ClientRequest;
import com.mybizcopilot.dto.responses.ClientResponse;
import com.mybizcopilot.entities.*;
import com.mybizcopilot.exception.OperationNonPermittedException;
import com.mybizcopilot.repositories.*;
import com.mybizcopilot.services.IClientService;
import com.mybizcopilot.utils.UtilService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ClientService implements IClientService {

    @Autowired
    private ObjectValidator<ClientRequest> clientValidator;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TrancheRepository trancheRepository;

    @Autowired
    private TypeprospectRepository typeprospectRepository;

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private UtilService utilService;

    @Autowired
    private CommandeRepository commandeRepository;

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final String PERSONNE_PHYSIQUE = "PERSONNE PHYSIQUE";

    private final String PERSONNE_MORALE = "PERSONNE MORALE";


    @Override
    @Transactional
    public Void ajouterClient(ClientRequest request) {
        clientValidator.validate(request);
        request.checkTypeProspect();

        Entreprise entreprise = entrepriseRepository.findById(request.getIdEntreprise()).orElseThrow(() -> new OperationNonPermittedException("Les informations de votre entreprise sont introuvables"));

        Utilisateur user = utilisateurRepository.findById(request.getIdUser()).orElseThrow(() -> new OperationNonPermittedException("Nous rencontrons une erreur avec votre compte"));

        if (user.getIdUtilisateur() != entreprise.getUtilisateur().getIdUtilisateur())
            throw new OperationNonPermittedException("Vous n'êtes pas autorisé à effectuer cette opération");

        Pays pays = paysRepository.findById(request.getPaysId()).orElseThrow(() -> new EntityNotFoundException("Le pays choisit est introuvable"));

        Client client = Client.builder()
                        .codeClient(utilService.generateClientCode())
                        .nomClient(request.getNom())
                        .telephoneUn(request.getTelephoneUn())
                        .telephoneDeux(request.getTelephoneDeux())
                        .emailUn(request.getEmailUn())
                        .emailDeux(request.getEmailDeux())
                        .isClient(request.getIsClient())
                        .entreprise(entreprise)
                        .typeClient(request.getTypeClient())
                        .agentLiaison(request.getAgentLiaison())
                        .pays(pays)
                        .ville(request.getVille())
                        .adresse(request.getAdresse())
                        .build();

        if (request.getIdTypeprospect() != 0 && request.getIdTypeprospect() != null){
            Typeprospect typeprospect = typeprospectRepository.findById(request.getIdTypeprospect()).orElseThrow(() -> new OperationNonPermittedException("Vous n'avez pas renseigner le type de prospect"));
            client.setTypeProspect(typeprospect);
        }
        if (request.getIdTranche() != null && request.getIdTranche() != 0) {
            Tranche tranche = trancheRepository.findById(request.getIdTranche()).orElseThrow(() -> new OperationNonPermittedException("La plage d'âge sélectionnée n'existe pas"));
            client.setTranche(tranche);
        }
        clientRepository.save(client);

        return null;
    }

    @Override
    public ClientResponse getClient(Integer idClient) {
        Client client = clientRepository.findById(idClient).orElseThrow(() -> {throw new EntityNotFoundException("Le client sélectionné est introuvable");
        });


        return ClientResponse.builder()
                .idClient(client.getIdClient())
                .code(client.getCodeClient())
                .emailUn(client.getEmailUn())
                .emailDeux(client.getEmailDeux())
                .telephoneUn(client.getTelephoneUn())
                .telephoneDeux(client.getTelephoneDeux())
                .nomClient(client.getNomClient())
                .statut(client.getIsClient() == 1 ? "Client" : client.getTypeProspect().getLibelleTypeprospect())
                .tranche(client.getTranche().getLibelleTranche())
                .ville(client.getVille())
                .adresse(client.getAdresse())
                .agentLiaison(client.getAgentLiaison())
                .typeClient(client.getTypeClient())
                .pays(client.getPays())
                .build();
    }

    @Override
    public List<ClientResponse> getAllClient(Integer idEntreprise) {
        Entreprise entreprise = entrepriseRepository.findById(idEntreprise).orElseThrow(() ->  new EntityNotFoundException("Aucune entreprise n'est retrouvée"));

        List<Client> clients = clientRepository.findAllByEntreprise(entreprise);
        List<ClientResponse> result = new ArrayList<>();
        for (Client client: clients) {
                result.add(
                        ClientResponse.builder()
                                .idClient(client.getIdClient())
                                .code(client.getCodeClient())
                                .emailUn(client.getEmailUn())
                                .emailDeux(client.getEmailDeux())
                                .telephoneUn(client.getTelephoneUn())
                                .telephoneDeux(client.getTelephoneDeux())
                                .nomClient(client.getNomClient())
                                .statut(client.getIsClient() == 1 ? "Client" : client.getTypeProspect().getLibelleTypeprospect())
                                .tranche(client.getTranche() != null ? client.getTranche().getLibelleTranche() : null)
                                .typeprospectId(client.getTypeProspect() != null ? client.getTypeProspect().getIdTypeprospect() : null)
                                .trancheId(client.getTranche() != null ? client.getTranche().getIdTranche() : null)
                                .ville(client.getVille())
                                .adresse(client.getAdresse())
                                .agentLiaison(client.getAgentLiaison())
                                .typeClient(client.getTypeClient())
                                .pays(client.getPays())
                                .build()
                );
        }
        return result;
    }

    @Override
    @Transactional
    public ClientResponse updateClient(Integer idClient, ClientRequest request) {
        clientValidator.validate(request);
        request.checkTypeProspect();

        Client client = clientRepository.findById(idClient).orElseThrow(() -> new EntityNotFoundException("Le client sélectionné est introuvable"));
        Entreprise entreprise = entrepriseRepository.findById(request.getIdEntreprise()).orElseThrow(() -> new OperationNonPermittedException("L'entreprise est introuvable"));

        Utilisateur user = utilisateurRepository.findById(request.getIdUser()).orElseThrow(() -> new OperationNonPermittedException("Nous rencontrons une erreur avec votre compte"));

        if (user.getIdUtilisateur() != entreprise.getUtilisateur().getIdUtilisateur())
            throw new OperationNonPermittedException("Vous n'êtes pas autorisé à effectuer cette opération");

        Typeprospect typeprospect = new Typeprospect();


        client.setNomClient(request.getNom());
        client.setTelephoneUn(request.getTelephoneUn());
        client.setTelephoneDeux(request.getTelephoneDeux());
        client.setEmailUn(request.getEmailUn());
        client.setEmailDeux(request.getEmailDeux());
        client.setIsClient(request.getIsClient());
        if (request.getIdTypeprospect() != 0 && request.getIdTypeprospect() != null){
            typeprospect = typeprospectRepository.findById(request.getIdTypeprospect()).orElseThrow(() -> new OperationNonPermittedException("Vous n'avez pas renseigner le type de prospect"));
            client.setTypeProspect(typeprospect);
        }else {
            client.setTypeProspect(null);
        }

        if (request.getIdTranche() != 0 && request.getIdTranche() != null){
            Tranche tranche = trancheRepository.findById(request.getIdTranche()).orElseThrow(() -> new OperationNonPermittedException("La plage d'âge sélectionnée n'existe pas"));
            client.setTranche(tranche);
        }else {
            client.setTranche(null);
        }

        Client savedClient = clientRepository.save(client);
        return ClientResponse.builder()
                .idClient(savedClient.getIdClient())
                .code(savedClient.getCodeClient())
                .emailUn(savedClient.getEmailUn())
                .emailDeux(savedClient.getEmailDeux())
                .telephoneUn(savedClient.getTelephoneUn())
                .telephoneDeux(savedClient.getTelephoneDeux())
                .nomClient(savedClient.getNomClient())
                .statut(savedClient.getIsClient() == 1 ? "Client" : client.getTypeProspect().getLibelleTypeprospect())
                .tranche(savedClient.getTranche() != null ? savedClient.getTranche().getLibelleTranche() : null)
                .ville(savedClient.getVille())
                .adresse(savedClient.getAdresse())
                .agentLiaison(savedClient.getAgentLiaison())
                .typeClient(savedClient.getTypeClient())
                .pays(savedClient.getPays())
                .build();
    }

    @Override
    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        List<ClientResponse> result = new ArrayList<>();

        if (!clients.isEmpty()) {
            for (Client client: clients) {
                result.add(
                        ClientResponse.builder()
                                .idClient(client.getIdClient())
                                .code(client.getCodeClient())
                                .emailUn(client.getEmailUn())
                                .emailDeux(client.getEmailDeux())
                                .nomClient(client.getNomClient())
                                .telephoneUn(client.getTelephoneUn())
                                .telephoneDeux(client.getTelephoneDeux())
                                .statut(client.getIsClient() == 1 ? "Client" : client.getTypeProspect().getLibelleTypeprospect())
                                .tranche(client.getTranche().getLibelleTranche())
                                .typeprospectId( client.getTypeProspect() != null ? client.getTypeProspect().getIdTypeprospect() : null)
                                .trancheId(client.getTranche().getIdTranche())
                                .ville(client.getVille())
                                .adresse(client.getAdresse())
                                .agentLiaison(client.getAgentLiaison())
                                .typeClient(client.getTypeClient())
                                .pays(client.getPays())
                                .build()
                );
            }
        }

        return result;
    }

    @Override
    public List<Pays> getAllPays() {
        return paysRepository.findAll();
    }


}
