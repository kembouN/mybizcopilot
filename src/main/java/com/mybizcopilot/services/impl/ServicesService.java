package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.ServiceRequest;
import com.mybizcopilot.dto.responses.ServiceResponse;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.exception.OperationNonPermittedException;
import com.mybizcopilot.repositories.EntrepriseRepository;
import com.mybizcopilot.repositories.ServiceRepository;
import com.mybizcopilot.services.IServicesService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ServicesService implements IServicesService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private ObjectValidator<ServiceRequest> serviceValidator;

    private static final Logger log = LoggerFactory.getLogger(ServicesService.class);


    @Override
    @Transactional
    public Void ajouterService(ServiceRequest request) {
        serviceValidator.validate(request);
        if (serviceRepository.countByLibelleService(request.getLibelle().toLowerCase().trim()) > 0){
            throw new OperationNonPermittedException("Un service avec ce libellé existe déjà");
        }

        Entreprise entreprise = entrepriseRepository.findById(request.getIdEntreprise())
                        .orElseThrow(() -> new EntityNotFoundException("Aucune entreprise correspondante"));
        if (!entreprise.getUtilisateur().getIdUtilisateur().equals(request.getIdUser()))
            throw new OperationNonPermittedException("Vous n'êtes pas autorisé à effectuer cette opération");
        serviceRepository.save(
                com.mybizcopilot.entities.Service.builder()
                        .descriptionService(request.getDescription())
                        .libelleService(request.getLibelle().trim())
                        .dureeInitiale(request.getDureeInitiale())
                        .entreprise(entreprise)
                        .prixInitial(request.getPrixInitial())
                        .build()
        );
        return null;
    }

    @Override
    public ServiceResponse afficherService(Integer idService) {
        com.mybizcopilot.entities.Service service = serviceRepository.findById(idService)
                .orElseThrow(()-> new EntityNotFoundException("Le service selectionné est introuvable"));

        return ServiceResponse.builder()
                .idService(service.getIdService())
                .prixInitial(service.getPrixInitial())
                .description(service.getDescriptionService())
                .libelle(service.getLibelleService())
                .dureeInitiale(service.getDureeInitiale())
                .qteInitiale(service.getQuantiteInitiale())
                .build();
    }

    @Override
    public List<ServiceResponse> listeServices(Integer idEntreprise) {
        log.info("Début pour liste des services");
        entrepriseRepository.findById(idEntreprise)
                .orElseThrow(() -> new EntityNotFoundException("Entreprise introuvable"));

        List<com.mybizcopilot.entities.Service> services = serviceRepository.findAllByEntrepriseIdEntreprise(idEntreprise);

        List<ServiceResponse> result = new ArrayList<>();
        if (!services.isEmpty()) {
            for (com.mybizcopilot.entities.Service service: services) {
                result.add(ServiceResponse.builder()
                        .idService(service.getIdService())
                        .description(service.getDescriptionService())
                        .libelle(service.getLibelleService())
                        .dureeInitiale(service.getDureeInitiale())
                        .prixInitial(service.getPrixInitial())
                        .qteInitiale(service.getQuantiteInitiale())
                        .build()
                );
            }
        }

        return result;
    }

    @Override
    public ServiceResponse updateService(Integer idService, ServiceRequest request) {
        if (serviceRepository.countByLibelleServiceWhereIdServiceNot(request.getLibelle().trim(), idService) > 0){
            log.error("Service existant");
            throw new OperationNonPermittedException("Un service avec ce libellé existe déjà");
        }

        com.mybizcopilot.entities.Service service = serviceRepository.findById(idService)
                .orElseThrow(() -> new EntityNotFoundException("Le service choisi est introuvable"));

        if (!service.getEntreprise().getIdEntreprise().equals(request.getIdEntreprise()))
            throw new OperationNonPermittedException("Vous ne pouvez pas apporter des modifications à ce service");

        service.setDescriptionService(request.getDescription());
        service.setLibelleService(request.getLibelle().trim());
        service.setDureeInitiale(request.getDureeInitiale());
        service.setPrixInitial(request.getPrixInitial());
        service.setQuantiteInitiale(request.getQteInitiale());
        com.mybizcopilot.entities.Service savedService = serviceRepository.save(service);

        return ServiceResponse.builder()
                .idService(savedService.getIdService())
                .description(savedService.getDescriptionService())
                .libelle(savedService.getLibelleService())
                .dureeInitiale(savedService.getDureeInitiale())
                .prixInitial(savedService.getPrixInitial())
                .qteInitiale(savedService.getQuantiteInitiale())
                .build();
    }
}
