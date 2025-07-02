package com.mybizcopilot.services.impl;


import com.mybizcopilot.config.JwtUtil;
import com.mybizcopilot.dto.requests.ChangePasswordRequest;
import com.mybizcopilot.dto.requests.LoginRequest;
import com.mybizcopilot.dto.requests.RegisterRequest;
import com.mybizcopilot.dto.requests.UpdateUserRequest;
import com.mybizcopilot.dto.responses.EntrepriseResponse;
import com.mybizcopilot.dto.responses.LoginResponse;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.entities.Utilisateur;
import com.mybizcopilot.exception.OperationNonPermittedException;
import com.mybizcopilot.repositories.EntrepriseRepository;
import com.mybizcopilot.repositories.UtilisateurRepository;
import com.mybizcopilot.services.IUtilisateurService;
import com.mybizcopilot.utils.UtilService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@Service
public class UtilisateurService implements IUtilisateurService {

    private ObjectValidator<RegisterRequest> registerValidator;

    private ObjectValidator<LoginRequest> loginValidator;

    private UtilisateurRepository utilisateurRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private UtilService utilService;

    private JwtUtil jwtUtil;

    private ObjectValidator<ChangePasswordRequest> changePasswordValidator;

    private ObjectValidator<UpdateUserRequest> updateUserValidator;

    private AuthenticationManager authManager;

    private EntrepriseRepository entrepriseRepository;

    private static final Logger log = LoggerFactory.getLogger(UtilisateurService.class);


    @Override
    @Transactional
    public Void register(RegisterRequest request) {
        registerValidator.validate(request);
        if (!request.getPass().equals(request.getCPass())) {
            log.error("Confirmation incorrecte");
            throw new OperationNonPermittedException("La confirmation du mot de passe est incorrecte");
        }

        if (utilisateurRepository.countByUsername(request.getEmail()) != 0) {
            log.error("Email utilisé");
            throw new OperationNonPermittedException("L'adresse e-mail est déjà utilisée");
        }

        Utilisateur user = new Utilisateur();
        user.setUsername(request.getEmail());
        user.setNomUtilisateur(request.getUsername());
        user.setMotpasse(passwordEncoder.encode(request.getPass()));
        user.setActiveUtilisateur(1);
        utilisateurRepository.save(user);
        //TODO Implémenter l'envoi d'email à utiliser pour vérification du compte
        return null;
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        loginValidator.validate(request);
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPass())
        );

        Utilisateur user = utilisateurRepository.findByUsername(request.getUsername()).get();
        if (user.getActiveUtilisateur() != 1){
            log.error("Compte inactif");
            throw new OperationNonPermittedException("Veuillez activer votre compte ou contactez les administrateurs");
        }
        user.setDerniereconnexion(LocalDate.now());
        utilisateurRepository.save(user);

        List<Entreprise> entreprises = entrepriseRepository.findAllByUtilisateurIdUtilisateur(user.getIdUtilisateur());
        List<EntrepriseResponse> entrepriseResponses = new ArrayList<>();
        for (Entreprise entreprise: entreprises) {
            if (entreprise != null){
                entrepriseResponses.add(
                        EntrepriseResponse.builder()
                                .ville(entreprise.getVille())
                                .pays(entreprise.getPays())
                                .nom(entreprise.getNomEntreprise())
                                .email(entreprise.getEmailEntreprise())
                                .entrepriseId(entreprise.getIdEntreprise())
                                .logo(entreprise.getLogoEntreprise() != null ? Base64.getEncoder().encodeToString(entreprise.getLogoEntreprise()) : "")
                                .build()
                );
            }
        }

        return LoginResponse.builder()
                .token(jwtUtil.createJwtToken(user))
                .email(user.getUsername())
                .username(user.getNomUtilisateur())
                .idUser(user.getIdUtilisateur())
                .lastconnexion(LocalDate.now())
                .entreprises(entrepriseResponses)
                .build();
    }

    @Override
    @Transactional
    public Void changePassword(Integer idUser, ChangePasswordRequest request) {

        changePasswordValidator.validate(request);
       request.checkPasswordConfirmation();

        Utilisateur user = utilisateurRepository.findById(idUser).orElseThrow(() -> {throw new EntityNotFoundException("Compte utilisateur introuvable");});
        if(!passwordEncoder.matches(request.getPassword(), user.getMotpasse())) {
            throw new OperationNonPermittedException("Mot de passe incorrect");
        }

        user.setMotpasse(passwordEncoder.encode(request.getNewPassword()));
        utilisateurRepository.save(user);

        return null;
    }

    @Override
    @Transactional
    public LoginResponse changeUserInfo(Integer idUser, UpdateUserRequest request){
        updateUserValidator.validate(request);
        Utilisateur user = utilisateurRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Informations du compte introuvables"));
        if (utilisateurRepository.countByUsernamExceptUser(idUser, request.getEmail()) > 0)
            throw new OperationNonPermittedException("Cet email est déjà utilisé");

        user.setNomUtilisateur(request.getNom().trim());
        user.setUsername(request.getEmail());

        Utilisateur saved = utilisateurRepository.save(user);
        return LoginResponse.builder()
                .username(user.getNomUtilisateur())
                .email(saved.getUsername())
                .build();
    }
}
