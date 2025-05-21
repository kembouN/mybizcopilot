package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.ClientRequest;
import com.mybizcopilot.dto.requests.EntrepriseRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.ClientResponse;
import com.mybizcopilot.dto.responses.EntrepriseResponse;
import com.mybizcopilot.services.IEntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/entreprise")
@Tag(name = "Entreprise")
public class EntrepriseController {

    private IEntrepriseService entrepriseService;

    @PostMapping
    @Operation(description = "Créer ou ajouter une entreprise")
    public ResponseEntity<BaseResponse<Void>> ajouterEntreprise(@RequestBody EntrepriseRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Entreprise ajoutée",
                        entrepriseService.ajouterEntreprise(request)
                )
        );
    }

    @GetMapping("/{idUtilisateur}")
    @Operation(description = "Afficher les entreprises d'un utilisateur")
    public ResponseEntity<BaseResponse<List<EntrepriseResponse>>> getEntreprises(@PathVariable Integer idUtilisateur) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        entrepriseService.getAllEntrepriseByUser(idUtilisateur)
                )
        );
    }

}
