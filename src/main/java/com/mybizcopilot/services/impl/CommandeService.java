package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.CommandeRequest;
import com.mybizcopilot.dto.responses.ClientResponse;
import com.mybizcopilot.dto.responses.CommandeResponse;
import com.mybizcopilot.dto.responses.ServiceResponse;
import com.mybizcopilot.entities.Client;
import com.mybizcopilot.entities.Commande;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.repositories.ClientRepository;
import com.mybizcopilot.repositories.CommandeRepository;
import com.mybizcopilot.repositories.EntrepriseRepository;
import com.mybizcopilot.repositories.ServiceRepository;
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

    private ServiceRepository serviceRepository;

    private ClientRepository clientRepository;

    private EntrepriseRepository entrepriseRepository;


    @Override
    @Transactional
    public Void ajouterCommande(CommandeRequest request) {
        commandeValidator.validate(request);
        request.checkSomeField();
        com.mybizcopilot.entities.Service service = serviceRepository.findById(request.getIdService())
                .orElseThrow(() -> new EntityNotFoundException("Le service sélectionné est introuvable"));

        Client client = clientRepository.findById(request.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException("Le client choisit est introuvable"));

        double cout = 0;
        if (request.getQte() != null)
            cout = (service.getPrixInitial() * request.getQte()) / service.getQuantiteInitiale();

        if (request.getDuree() != null)
            cout = (request.getDuree() * service.getPrixInitial()) / service.getDureeInitiale();

        //if (request.getDateFin() == null && (request.getStatut() == "TERMINEE" || request.getStatut() == "LIVREE"))

        commandeRepository.save(
                Commande.builder()
                        .service(service)
                        .client(client)
                        .avanceCout(request.getAvance())
                        .cout(cout)
                        .dateAvance(request.getDateAvance())
                        .dateContact(request.getDateContact())
                        .dateDebut(request.getDateDebut())
                        .quantite(request.getQte())
                        .duree(request.getDuree())
                        .paye(request.isPaye() ? 1 : 0)
                        .datePaiement(request.getDatePaiement() != null ? request.getDatePaiement() : null)
                        .statutCommande(!request.getStatut().isEmpty() ? request.getStatut() : "EN ATTENTE")
                        .dateFin(request.getDateFin())
                        .build()
        );
        return null;
    }

    @Override
    public CommandeResponse afficherCommande(Integer idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(()-> new EntityNotFoundException("La commande sélectionnée est introuvable"));

        return CommandeResponse.builder()
                .idCommande(commande.getIdCommande())
                .client(commande.getService().getLibelleService())
                .service(commande.getClient().getNomClient())
                .dateCommande(commande.getDateContact())
                .dateDebut(commande.getDateDebut())
                .dateFin(commande.getDateFin())
                .qte(commande.getQuantite())
                .duree(commande.getDuree())
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
        List<Commande> commandes = commandeRepository.findAllByServiceEntrepriseIdEntreprise(idEntreprise);

        List<CommandeResponse> result = new ArrayList<>();

        if (!commandes.isEmpty()){
            for (Commande commande: commandes) {
                result.add(
                        CommandeResponse.builder()
                                .idCommande(commande.getIdCommande())
                                .service(commande.getService().getLibelleService())
                                .client(commande.getClient().getNomClient())
                                .dateCommande(commande.getDateContact())
                                .dateDebut(commande.getDateDebut())
                                .dateFin(commande.getDateFin())
                                .qte(commande.getQuantite())
                                .duree(commande.getDuree())
                                .avance(commande.getAvanceCout())
                                .cout(commande.getCout())
                                .dateAvance(commande.getDateAvance())
                                .paye(commande.getPaye())
                                .statutCommande(commande.getStatutCommande())
                                .build()
                );
            }

        }
        return result;
    }

    @Override
    public CommandeResponse updateCommande(Integer idCommande, CommandeRequest request) {
        return null;
    }
}
