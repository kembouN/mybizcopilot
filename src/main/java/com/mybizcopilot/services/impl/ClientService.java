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
    private UtilService utilService;

    @Autowired
    private CommandeRepository commandeRepository;


    @Override
    @Transactional
    public Void ajouterClient(ClientRequest request) {
        clientValidator.validate(request);
        request.checkTypeProspect();

        //Entreprise entreprise = entrepriseRepository.findById(request.getIdEntreprise()).get();//.orElseThrow(() -> {throw new OperationNonPermittedException("L'entreprise est introuvable");

        //Utilisateur user = utilisateurRepository.findById(request.getIdUser()).get();//.orElseThrow(() -> {throw new OperationNonPermittedException("Nous rencontrons une erreur avec votre compte");});

        /*if (user.getIdUtilisateur() != entreprise.getUtilisateur().getIdUtilisateur())
            throw new OperationNonPermittedException("Vous n'êtes pas autorisé à effectuer cette opération");*/

        Tranche tranche = trancheRepository.findById(request.getIdTranche()).orElseThrow(() -> new OperationNonPermittedException("La plage d'âge sélectionnée n'existe pas"));

        Client client = Client.builder()
                        .codeClient(utilService.generateClientCode())
                        .tranche(tranche)
                        .nomClient(request.getNom())
                        .telephoneUn(request.getTelephoneUn())
                        .telephoneDeux(request.getTelephoneDeux())
                        .emailUn(request.getEmailUn())
                        .emailDeux(request.getEmailDeux())
                        .isClient(request.isClient() ? 1 : 0)
                        .build();
        if (request.getIdTypeprospect() != 0 && request.getIdTypeprospect() != null){
            Typeprospect typeprospect = typeprospectRepository.findById(request.getIdTypeprospect()).orElseThrow(() -> new OperationNonPermittedException("Vous n'avez pas renseigner le type de prospect"));
            client.setTypeProspect(typeprospect);
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
                .build();
    }

    @Override
    public List<ClientResponse> getAllClient(Integer idEntreprise) {
        Entreprise entreprise = entrepriseRepository.findById(idEntreprise).orElseThrow(() -> {throw new EntityNotFoundException("Aucune entreprise n'est retrouvée");});

        List<Commande> commandes = commandeRepository.findDistinctByServiceEntrepriseIdEntreprise(idEntreprise);

        List<ClientResponse> clients = new ArrayList<>();
        for (Commande commande: commandes) {
            Client client = clientRepository.findById(commande.getClient().getIdClient()).get();
            if (client != null) {
                clients.add(
                        ClientResponse.builder()
                                .idClient(client.getIdClient())
                                .code(client.getCodeClient())
                                .emailUn(client.getEmailUn())
                                .emailDeux(client.getEmailDeux())
                                .telephoneUn(client.getTelephoneUn())
                                .telephoneDeux(client.getTelephoneDeux())
                                .nomClient(client.getNomClient())
                                .statut(client.getIsClient() == 1 ? "Client" : client.getTypeProspect().getLibelleTypeprospect())
                                .tranche(client.getTranche().getLibelleTranche())
                                .build()
                );
            }
        }
        return clients;
    }

    @Override
    public ClientResponse updateClient(Integer idClient, ClientRequest request) {
        clientValidator.validate(request);
        request.checkTypeProspect();
        Client client = clientRepository.findById(idClient).orElseThrow(() -> {throw new EntityNotFoundException("Le client sélectionné est introuvable");});

        Entreprise entreprise = entrepriseRepository.findById(request.getIdEntreprise()).orElseThrow(() -> {throw new OperationNonPermittedException("L'entreprise est introuvable");
        });

        Utilisateur user = utilisateurRepository.findById(request.getIdUser()).orElseThrow(() -> {throw new OperationNonPermittedException("Nous rencontrons une erreur avec votre compte");});

        if (user.getIdUtilisateur() != entreprise.getUtilisateur().getIdUtilisateur())
            throw new OperationNonPermittedException("Vous n'êtes pas autorisé à effectuer cette opération");

        Tranche tranche = trancheRepository.findById(request.getIdTranche()).orElseThrow(() -> {throw new OperationNonPermittedException("La plage d'âge sélectionnée n'existe pas");});
        Typeprospect typeprospect = new Typeprospect();

        if (!request.isClient())
            typeprospect = typeprospectRepository.findById(request.getIdTypeprospect()).orElseThrow(() -> {throw new OperationNonPermittedException("Vous n'avez pas renseigner le type de prospect");});

        client.setTranche(tranche);
        client.setTypeProspect(typeprospect);
        client.setNomClient(request.getNom());
        client.setTelephoneUn(request.getTelephoneUn());
        client.setTelephoneDeux(request.getTelephoneDeux());
        client.setEmailUn(request.getEmailUn());
        client.setEmailDeux(request.getEmailDeux());
        client.setIsClient(request.isClient() ? 1 : 0);

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
                .tranche(savedClient.getTranche().getLibelleTranche())
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
                                .build()
                );
            }
        }

        return result;
    }

}
