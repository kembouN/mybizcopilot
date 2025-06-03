package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.CommandeRequest;
import com.mybizcopilot.dto.requests.ElementCommandeRequest;
import com.mybizcopilot.dto.responses.CommandeResponse;
import com.mybizcopilot.dto.responses.ElementCommandeDto;
import com.mybizcopilot.entities.*;
import com.mybizcopilot.exception.OperationNonPermittedException;
import com.mybizcopilot.repositories.*;
import com.mybizcopilot.services.ICommandeService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommandeService implements ICommandeService {

    private CommandeRepository commandeRepository;

    private ObjectValidator<CommandeRequest> commandeValidator;

    private SousServiceRepository sousServiceRepository;

    private ClientRepository clientRepository;

    private ElementCommandeRepository elementCommandeRepository;

    private EntrepriseRepository entrepriseRepository;


    @Override
    @Transactional
    public Void ajouterCommande(CommandeRequest request) {
        commandeValidator.validate(request);
        request.checkSomeField();

        Client client = clientRepository.findById(request.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException("Le client choisit est introuvable"));

        Double montant = 0.0;
        if (request.getItems().isEmpty() || request.getItems().size() == 0)
            throw new OperationNonPermittedException("Aucun service n'est commandé");
        List<ElementCommande> elementCommandes = new ArrayList<>();
        for (ElementCommandeDto element : request.getItems()) {
            SousService sousService = sousServiceRepository.findById(element.getIdSousservice()).get();
            if (sousService != null) {
                montant += element.getQuantite() * element.getPrix();
                elementCommandes.add(
                        com.mybizcopilot.entities.ElementCommande.builder()
                                .sousService(sousService)
                                .quantite(element.getQuantite())
                                .prix(element.getPrix())
                                .build()
                );
            }
        }

        Commande commande = commandeRepository.save(
                Commande.builder()
                        .client(client)
                        .avanceCout(request.getAvance())
                        .cout(montant)
                        .dateAvance(request.getDateAvance())
                        .dateContact(request.getDateContact())
                        .paye(request.getPaye())
                        .datePaiement(request.getDatePaiement() != null ? request.getDatePaiement() : null)
                        .statutCommande(StatutCommande.EN_ATTENTE)
                        .dateFin(request.getDateFin())
                        .build()
        );
        for (com.mybizcopilot.entities.ElementCommande elementComande : elementCommandes) {
            elementComande.setCommande(commande);
            elementCommandeRepository.save(elementComande);
        }

        return null;
    }

    @Override
    public CommandeResponse afficherCommande(Integer idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(()-> new EntityNotFoundException("La commande sélectionnée est introuvable"));

        return CommandeResponse.builder()
                .idCommande(commande.getIdCommande())
                .dateCommande(commande.getDateContact())
                .dateFin(commande.getDateFin())
                .avance(commande.getAvanceCout())
                .cout(commande.getCout())
                .dateAvance(commande.getDateAvance())
                .paye(commande.getPaye())
                .statutCommande(commande.getStatutCommande())
                .build();
    }

    @Override
    public List<CommandeResponse> listeCommandes(Integer idEntreprise) {
        Entreprise entreprise = entrepriseRepository.findById(idEntreprise)
                .orElseThrow(()-> new EntityNotFoundException("Nous ne trouvons aucune information sur l'entreprise"));
        List<Commande> commandes = commandeRepository.findAllByClientEntrepriseIdEntreprise(idEntreprise);

        List<CommandeResponse> result = new ArrayList<>();

        if (!commandes.isEmpty()){
            for (Commande commande: commandes) {
                List<ElementCommandeDto> elements = new ArrayList<>();
                for (com.mybizcopilot.entities.ElementCommande elementCmd : commande.getElementsCommande()) {
                    elements.add(
                            ElementCommandeDto.builder()
                                    .idElement(elementCmd.getIdElementcommande())
                                    .prix(elementCmd.getPrix())
                                    .quantite(elementCmd.getQuantite())
                                    .idSousservice(elementCmd.getSousService().getIdSousservice())
                                    .build()
                    );
                }
                result.add(
                        CommandeResponse.builder()
                                .idCommande(commande.getIdCommande())
                                .idClient(commande.getClient().getIdClient())
                                .client(commande.getClient().getNomClient())
                                .dateCommande(commande.getDateContact())
                                .dateFin(commande.getDateFin())
                                .avance(commande.getAvanceCout())
                                .cout(commande.getCout())
                                .dateAvance(commande.getDateAvance())
                                .paye(commande.getPaye())
                                .statutCommande(commande.getStatutCommande())
                                .elements(elements)
                                .build()
                );
            }

        }
        return result;
    }

    @Override
    @Transactional
    public Void updateCommande(Integer idCommande, CommandeRequest request) {
        commandeValidator.validate(request);
        request.checkSomeField();

        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new EntityNotFoundException("La commande sélectionnée est introuvable"));

        Client client = clientRepository.findById(request.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException("Le client est introuvable"));
        Double montant = 0.0;
        if (request.getItems().isEmpty() || request.getItems().size() == 0)
            throw new OperationNonPermittedException("Aucun service n'est commandé");

        for (ElementCommandeDto item : request.getItems()) {
            ElementCommande elementCommande = elementCommandeRepository.findByIdElementcommande(commande.getIdCommande());
            SousService sousService = sousServiceRepository.findById(item.getIdSousservice()).get();
            montant += item.getQuantite() * item.getPrix();

            if (elementCommande != null && sousService != null){
                elementCommande.setCommande(commande);
                elementCommande.setPrix(item.getPrix());
                elementCommande.setQuantite(item.getQuantite());
                elementCommande.setSousService(sousService);
                elementCommandeRepository.save(elementCommande);
            } else if (elementCommande == null && sousService != null) {
                elementCommandeRepository.save(ElementCommande.builder()
                                .quantite(item.getQuantite())
                                .sousService(sousService)
                                .commande(commande)
                                .prix(item.getPrix())
                        .build()
                );
            }
        }
        commande.setClient(client);
        commande.setAvanceCout(request.getAvance());
        commande.setCout(montant);
        commande.setDateAvance(request.getDateAvance());
        commande.setDateContact(request.getDateContact());
        commande.setPaye(request.getPaye());
        commande.setDatePaiement(request.getDatePaiement());
        commande.setDateFin(request.getDateFin());

        commandeRepository.save(commande);

        return null;
    }
}
