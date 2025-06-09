package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.CollaborateurRequest;
import com.mybizcopilot.dto.responses.CollaborateurResponse;
import com.mybizcopilot.entities.Collaborateur;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.entities.Pays;
import com.mybizcopilot.entities.StatutCommande;
import com.mybizcopilot.repositories.CollaborateurRepository;
import com.mybizcopilot.repositories.CommandeRepository;
import com.mybizcopilot.repositories.EntrepriseRepository;
import com.mybizcopilot.repositories.PaysRepository;
import com.mybizcopilot.services.ICollaborateurService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CollaborateurService implements ICollaborateurService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Autowired
    private ObjectValidator<CollaborateurRequest> collaborateurValidator;

    @Autowired
    private CommandeRepository commandeRepository;


    @Override
    @Transactional
    public Void ajouterCollaborateur(CollaborateurRequest request) {
        collaborateurValidator.validate(request);
        request.checkNumbers();
        Entreprise entreprise = entrepriseRepository.findById(request.getIdEntreprise())
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'entreprise introuvables"));

        Pays pays = paysRepository.findById(request.getIdPays())
                .orElseThrow(() -> new EntityNotFoundException("Le pays est introuvable"));

        collaborateurRepository.save(
                Collaborateur.builder()
                        .entreprise(entreprise)
                        .pays(pays)
                        .nom(request.getNomCollaborateur())
                        .ville(request.getVille())
                        .adresse(request.getAdresse())
                        .telephoneUn(request.getTelephoneUn())
                        .telephoneDeux(request.getTelephoneDeux())
                        .build()
        );

        return null;
    }

    @Override
    public List<CollaborateurResponse> getAllCollaborateurs(Integer idEntreprise) {
        Entreprise entreprise = entrepriseRepository.findById(idEntreprise)
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'entreprise introuvables"));

        List<Collaborateur> collaborateurs = collaborateurRepository.findAllByEntreprise(entreprise);

        List<CollaborateurResponse> result = new ArrayList<>();
        for (Collaborateur collaborateur : collaborateurs){
            int tache = commandeRepository.countByCollaborateurAndStatutCommandeIsNotLike(collaborateur, StatutCommande.TERMINEE);
            result.add(
                    CollaborateurResponse.builder()
                            .idCollaborateur(collaborateur.getIdCollaborateur())
                            .pays(collaborateur.getPays())
                            .nom(collaborateur.getNom())
                            .telephoneUn(collaborateur.getTelephoneUn())
                            .telephoneDeux(collaborateur.getTelephoneDeux())
                            .adresse(collaborateur.getAdresse())
                            .ville(collaborateur.getVille())
                            .tache(tache)
                            .build()
            );
        }
        return result;
    }


    @Override
    @Transactional
    public Void updateCollaborateur(Integer idCollaborateur, CollaborateurRequest request) {
        collaborateurValidator.validate(request);
        request.checkNumbers();
        Collaborateur collaborateur = collaborateurRepository.findById(idCollaborateur)
                .orElseThrow(() -> new EntityNotFoundException("Collaborateur sélectionné introuvable"));
        Entreprise entreprise = entrepriseRepository.findById(request.getIdEntreprise())
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'entreprise introuvables"));

        Pays pays = paysRepository.findById(request.getIdPays())
                .orElseThrow(() -> new EntityNotFoundException("Le pays est introuvable"));

        collaborateur.setEntreprise(entreprise);
        collaborateur.setPays(pays);
        collaborateur.setNom(request.getNomCollaborateur());
        collaborateur.setTelephoneUn(request.getTelephoneUn());
        collaborateur.setTelephoneDeux(request.getTelephoneDeux());
        collaborateur.setVille(request.getVille());
        collaborateur.setAdresse(request.getAdresse());

        collaborateurRepository.save(collaborateur);

        return null;
    }
}
