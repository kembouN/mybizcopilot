package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.ServiceRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.ServiceResponse;
import com.mybizcopilot.services.IEntrepriseService;
import com.mybizcopilot.services.IServicesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/service")
public class ServicesController {

    private IServicesService service;

    @PostMapping
    @Operation(description = "Ajouter un nouveau service à l'entreprise")
    public ResponseEntity<BaseResponse<Void>> ajouterService(@RequestBody ServiceRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Service ajouté",
                        service.ajouterService(request)
                )
        );
    }

    @GetMapping("/{idService}")
    @Operation(description = "Obtenir les détails d'un service")
    public ResponseEntity<BaseResponse<ServiceResponse>> getService(@PathVariable Integer idService) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Service récupéré",
                        service.afficherService(idService)
                )
        );
    }

    @GetMapping("/getBy/entreprise/{idEntreprise}")
    @Operation(description = "Afficher la liste des services d'une entreprise")
    public ResponseEntity<BaseResponse<List<ServiceResponse>>> getAllServices(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Service récupéré",
                        service.listeServices(idEntreprise)
                )
        );
    }

    @PutMapping("/{idService}")
    @Operation(description = "Mettre à jour un service")
    public ResponseEntity<BaseResponse<ServiceResponse>> updateService(@PathVariable Integer idService, @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Service modifié",
                        service.updateService(idService, request)
                )
        );
    }



}
