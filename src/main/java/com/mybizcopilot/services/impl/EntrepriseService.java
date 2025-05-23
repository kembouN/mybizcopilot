package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.EntrepriseRequest;
import com.mybizcopilot.dto.responses.EntrepriseResponse;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.entities.Utilisateur;
import com.mybizcopilot.exception.OperationNonPermittedException;
import com.mybizcopilot.repositories.EntrepriseRepository;
import com.mybizcopilot.repositories.UtilisateurRepository;
import com.mybizcopilot.services.IEntrepriseService;
import com.mybizcopilot.utils.UtilService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class EntrepriseService implements IEntrepriseService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private ObjectValidator<EntrepriseRequest> entrepriseValidator;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilService utilService;

    private static final Logger log = LoggerFactory.getLogger(EntrepriseService.class);

    @Override
    public Void ajouterEntreprise(EntrepriseRequest request) {
        entrepriseValidator.validate(request);
        Utilisateur user = utilisateurRepository.findById(request.getIdUser()).orElseThrow(() -> {throw new EntityNotFoundException("Compte introuvable");
        });
        if (entrepriseRepository.countByNomEntreprise(request.getNom().trim()) > 0)
            throw new OperationNonPermittedException("Une entreprise de même nom existe déjà");

        entrepriseRepository.save(
                Entreprise.builder()
                        .codeEntreprise(utilService.generateEnterpriseCode())
                        .descriptionEntreprise(request.getDescription())
                        .emailEntreprise(request.getEmail())
                        .pays(request.getPays())
                        .ville(request.getVille())
                        .nomEntreprise(request.getNom().trim())
                        .utilisateur(user)
                        .telephone1Entreprise(request.getTelephone1())
                        .telephone2Entreprise(request.getTelephone2())
                        .build()
        );
        return null;
    }

    @Override
    public EntrepriseResponse getEntreprise(Integer idEntreprise) {
        Entreprise entreprise = entrepriseRepository.findById(idEntreprise)
                .orElseThrow(() -> {throw new EntityNotFoundException("L'entreprise sélectionnée est introuvable");});

        return EntrepriseResponse.builder()
                .entrepriseId(entreprise.getIdEntreprise())
                .code(entreprise.getCodeEntreprise())
                .description(entreprise.getDescriptionEntreprise())
                .email(entreprise.getEmailEntreprise())
                .pays(entreprise.getPays())
                .ville(entreprise.getVille())
                .logo(entreprise.getLogoEntreprise())
                .nom(entreprise.getNomEntreprise())
                .telephone1(entreprise.getTelephone1Entreprise())
                .telephone2(entreprise.getTelephone2Entreprise())
                .responsable(entreprise.getUtilisateur().getUsername())
                .build();
    }

    @Override
    public List<EntrepriseResponse> getAllEntrepriseByUser(Integer idUser) {
        log.info("debut de récupération...");
        Utilisateur utilisateur = utilisateurRepository.findById(idUser)
                .orElseThrow(()-> {log.error("Utilisateur introuvable"); throw new EntityNotFoundException("Compte utilisateur introuvable");});

        List<Entreprise> entreprises = entrepriseRepository.findAllByUtilisateurIdUtilisateur(idUser);

        List<EntrepriseResponse> result = new ArrayList<>();

        if (!entreprises.isEmpty()){
            for (Entreprise enterprise: entreprises) {
                result.add(
                        EntrepriseResponse.builder()
                                .entrepriseId(enterprise.getIdEntreprise())
                                .code(enterprise.getCodeEntreprise())
                                .email(enterprise.getEmailEntreprise())
                                .pays(enterprise.getPays())
                                .ville(enterprise.getVille())
                                .description(enterprise.getDescriptionEntreprise())
                                .logo(enterprise.getLogoEntreprise())
                                .nom(enterprise.getNomEntreprise())
                                .telephone1(enterprise.getTelephone1Entreprise())
                                .telephone2(enterprise.getTelephone2Entreprise())
                                .responsable(enterprise.getUtilisateur().getUsername())
                                .build()
                );
            }
        }

        return result;
    }

    @Override
    @Transactional
    public Entreprise updateEntreprise(Integer idEnterprise, EntrepriseRequest request) {
        entrepriseValidator.validate(request);
        Entreprise entreprise = entrepriseRepository.findById(idEnterprise)
                .orElseThrow(() -> {throw new EntityNotFoundException("L'entreprise est introuvable");});

        Utilisateur user = utilisateurRepository.findById(request.getIdUser())
                .orElseThrow(() -> {throw new OperationNonPermittedException("Le compte utilisateur est introuvable");
                });
        if (!entreprise.getUtilisateur().getIdUtilisateur().equals(request.getIdUser())){
            throw new OperationNonPermittedException("Vous n'êtes pas autorisé à effectuer cette opération");
        }

        entreprise.setDescriptionEntreprise(request.getDescription());
        entreprise.setEmailEntreprise(request.getEmail());
        entreprise.setPays(request.getPays());
        entreprise.setVille(request.getVille());
        entreprise.setNomEntreprise(request.getNom());
        entreprise.setTelephone1Entreprise(request.getTelephone1());
        entreprise.setTelephone2Entreprise(request.getTelephone2());

        return entrepriseRepository.save(entreprise);
    }
}
