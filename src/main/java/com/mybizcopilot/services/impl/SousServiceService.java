package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.SousServiceRequest;
import com.mybizcopilot.dto.responses.SousServiceResponse;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.entities.SousService;
import com.mybizcopilot.exception.OperationNonPermittedException;
import com.mybizcopilot.repositories.EntrepriseRepository;
import com.mybizcopilot.repositories.ServiceRepository;
import com.mybizcopilot.repositories.SousServiceRepository;
import com.mybizcopilot.services.ISousServiceService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SousServiceService implements ISousServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SousServiceRepository sousServiceRepository;

    @Autowired
    private ObjectValidator<SousServiceRequest> sousServiceValidator;

    @Autowired
    private EntrepriseRepository entrepriseRepository;


    @Override
    @Transactional
    public Void ajouterSousService(SousServiceRequest request) {
        sousServiceValidator.validate(request);
        com.mybizcopilot.entities.Service service = serviceRepository.findById(request.getIdService())
                .orElseThrow(() -> new EntityNotFoundException("Le service correspondant est introuvable"));
        if (sousServiceRepository.countAllByLibelleIgnoreCase(request.getLibelle().trim()) > 0) {
            throw new OperationNonPermittedException("Un sous-service ayant ce libellé existe déjà dans ce service ");
        }

        sousServiceRepository.save(
                SousService.builder()
                        .service(service)
                        .description(request.getDescription())
                        .duree(request.getDuree())
                        .uniteDuree(request.getUnite())
                        .libelle(request.getLibelle())
                        .build()
        );
        return null;
    }

    @Override
    public List<SousServiceResponse> getAllSousServiceByEnterprise(Integer etsId) {
        Entreprise entreprise = entrepriseRepository.findById(etsId)
                .orElseThrow(() -> new EntityNotFoundException("L'entreprise est introuvable"));

        List<SousService> sousServices = sousServiceRepository.findAllByServiceEntreprise(entreprise);

        List<SousServiceResponse> result = new ArrayList<>();
        for (SousService sousService: sousServices) {
            result.add(
                    SousServiceResponse.builder()
                            .idSousService(sousService.getIdSousservice())
                            .libelle(sousService.getLibelle())
                            .description(sousService.getDescription())
                            .duree(sousService.getDuree())
                            .unite(sousService.getUniteDuree())
                            .idService(sousService.getService().getIdService())
                            .libelleService(sousService.getService().getLibelleService())
                            .build()
            );
        }
        return result;
    }

    @Override
    public SousServiceResponse getSousService(Integer idSousService) {
        return null;
    }

    @Override
    public Void updateSousService(Integer idSousService, SousServiceRequest request) {

        sousServiceValidator.validate(request);
        SousService sousService = sousServiceRepository.findById(idSousService)
                .orElseThrow(() -> new EntityNotFoundException("Le sous-service sélectionné est introuvable"));
        com.mybizcopilot.entities.Service service = serviceRepository.findById(request.getIdService())
                .orElseThrow(() -> new EntityNotFoundException("Le service correspondant est introuvable"));
        if (sousServiceRepository.countAllSousServiceWithLibelleAndNotWithId(request.getLibelle().trim(), idSousService) > 0) {
            throw new OperationNonPermittedException("Un sous-service ayant ce libellé existe déjà dans ce service ");
        }

        sousService.setService(service);
        sousService.setLibelle(request.getLibelle().trim());
        sousService.setDescription(request.getDescription());
        sousService.setDuree(request.getDuree());
        sousService.setUniteDuree(request.getUnite());

        sousServiceRepository.save(sousService);
        return null;
    }
}
