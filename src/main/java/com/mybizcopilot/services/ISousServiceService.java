package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.SousServiceRequest;
import com.mybizcopilot.dto.responses.SousServiceResponse;

import java.util.List;

public interface ISousServiceService {

    Void ajouterSousService(SousServiceRequest request);

    List<SousServiceResponse> getAllSousServiceByEnterprise(Integer etsId);

    SousServiceResponse getSousService(Integer idSousService);

    Void updateSousService(Integer idSousService, SousServiceRequest request);
}
