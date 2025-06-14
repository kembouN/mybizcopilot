package com.mybizcopilot.services.impl;

import com.google.i18n.phonenumbers.NumberParseException;
import com.mybizcopilot.dto.requests.EntrepriseRequest;
import com.mybizcopilot.dto.responses.EntrepriseResponse;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.entities.Pays;
import com.mybizcopilot.entities.Utilisateur;
import com.mybizcopilot.exception.OperationNonPermittedException;
import com.mybizcopilot.repositories.ClientRepository;
import com.mybizcopilot.repositories.EntrepriseRepository;
import com.mybizcopilot.repositories.PaysRepository;
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
    private ClientRepository clientRepository;

    @Autowired
    private UtilService utilService;

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private PaysRepository paysRepository;

    private static final Logger log = LoggerFactory.getLogger(EntrepriseService.class);

    @Override
    public Void ajouterEntreprise(EntrepriseRequest request) {
        entrepriseValidator.validate(request);
        Utilisateur user = utilisateurRepository.findById(request.getIdUser()).orElseThrow(() -> {throw new EntityNotFoundException("Compte introuvable");
        });
        if (entrepriseRepository.countByNomEntreprise(request.getNom().trim()) > 0)
            throw new OperationNonPermittedException("Une entreprise de même nom existe déjà");

        Pays pays = paysRepository.findById(request.getIdPays()).orElseThrow(() -> new EntityNotFoundException("Le pays choisit est introuvable"));
        checkNumberValidity(pays, request.getTelephone1(), request.getTelephone2());
        entrepriseRepository.save(
                Entreprise.builder()
                        .codeEntreprise(utilService.generateEnterpriseCode())
                        .descriptionEntreprise(request.getDescription())
                        .emailEntreprise(request.getEmail())
                        .pays(pays)
                        .ville(request.getVille())
                        .adresse(request.getAdresse())
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
                .adresse(entreprise.getAdresse())
                .logo(entreprise.getLogoEntreprise())
                .nom(entreprise.getNomEntreprise())
                .telephone1(entreprise.getTelephone1Entreprise())
                .telephone2(entreprise.getTelephone2Entreprise())
                .responsable(entreprise.getUtilisateur().getUsername())
                .build();
    }

    @Override
    public List<EntrepriseResponse> getAllEntrepriseByUser(Integer idUser) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUser)
                .orElseThrow(()-> new EntityNotFoundException("Compte utilisateur introuvable"));

        List<Entreprise> entreprises = entrepriseRepository.findAllByUtilisateurIdUtilisateur(idUser);

        List<EntrepriseResponse> result = new ArrayList<>();

        if (!entreprises.isEmpty()){
            for (Entreprise enterprise: entreprises) {
                String number1 = "";
                String number2 = "";
                try{
                    if (!enterprise.getTelephone1Entreprise().isEmpty())
                        number1 = phoneNumberService.formatForDisplay(enterprise.getTelephone1Entreprise(), enterprise.getPays().getAbreviationPays());
                    if (!enterprise.getTelephone2Entreprise().isEmpty())
                        number2 = phoneNumberService.formatForDisplay(enterprise.getTelephone2Entreprise(), enterprise.getPays().getAbreviationPays());
                }catch (NumberParseException e){
                    e.printStackTrace();
                }

                result.add(
                        EntrepriseResponse.builder()
                                .entrepriseId(enterprise.getIdEntreprise())
                                .code(enterprise.getCodeEntreprise())
                                .email(enterprise.getEmailEntreprise())
                                .pays(enterprise.getPays())
                                .ville(enterprise.getVille())
                                .adresse(enterprise.getAdresse())
                                .description(enterprise.getDescriptionEntreprise())
                                .logo(enterprise.getLogoEntreprise())
                                .nom(enterprise.getNomEntreprise())
                                .telephone1(number1)
                                .telephone2(number2)
                                .responsable(enterprise.getUtilisateur().getUsername())
                                .nbrClient(clientRepository.findAllByEntreprise(enterprise).size())
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
        Pays pays = paysRepository.findById(request.getIdPays()).orElseThrow(() -> new EntityNotFoundException("Le pays choisit est introuvable"));

        entreprise.setDescriptionEntreprise(request.getDescription());
        entreprise.setEmailEntreprise(request.getEmail());
        entreprise.setPays(pays);
        entreprise.setVille(request.getVille());
        entreprise.setAdresse(request.getAdresse());
        entreprise.setNomEntreprise(request.getNom());
        entreprise.setTelephone1Entreprise(request.getTelephone1());
        entreprise.setTelephone2Entreprise(request.getTelephone2());

        return entrepriseRepository.save(entreprise);
    }

    private void checkNumberValidity(Pays pays, String number1, String number2) {
        if (!number1.isEmpty() && !phoneNumberService.isValidPhoneNumber(number1, pays.getAbreviationPays()))
            throw new IllegalArgumentException("Le téléphone n°1 est invalide");

        if (!number2.isEmpty() && !phoneNumberService.isValidPhoneNumber(number2, pays.getAbreviationPays()))
            throw new IllegalArgumentException("Le téléphone n°2 est invalide");

    }

}
