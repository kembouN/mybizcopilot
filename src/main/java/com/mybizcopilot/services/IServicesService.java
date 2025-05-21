package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.ServiceRequest;
import com.mybizcopilot.dto.responses.ServiceResponse;

import java.util.List;

public interface IServicesService {

    Void ajouterService(ServiceRequest request);

    ServiceResponse afficherService(Integer idService);

    List<ServiceResponse> listeServices(Integer idEntreprise);

    ServiceResponse updateService(Integer idService, ServiceRequest request);
}
