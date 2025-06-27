package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.ClientRequest;
import com.mybizcopilot.dto.requests.EntrepriseRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.ClientResponse;
import com.mybizcopilot.dto.responses.EntrepriseResponse;
import com.mybizcopilot.entities.Entreprise;
import com.mybizcopilot.services.IEntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/user/{idUtilisateur}")
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

    @GetMapping("/{idEntreprise}")
    public ResponseEntity<BaseResponse<EntrepriseResponse>> getSpecificEntreprise(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        entrepriseService.getEntreprise(idEntreprise)
                )
        );
    }

    @PutMapping("/{idEntreprise}")
    @Operation(description = "Mettre à jour une entreprise")
    public ResponseEntity<BaseResponse<String>> updateEnterprise(@PathVariable Integer idEntreprise, @RequestBody EntrepriseRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Mise à jour effectuée",
                        entrepriseService.updateEntreprise(idEntreprise, request)
                )
        );
    }

    @PostMapping("/{idEntreprise}/upload-logo")
    @Operation(description = "Ajouter un logo à l'entreprise")
    public ResponseEntity<BaseResponse<Void>> uploadLogo(@PathVariable Integer idEntreprise, @RequestParam("logo")MultipartFile logo) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Logo mis à jour",
                        entrepriseService.uploadLogo(idEntreprise, logo)
                )
        );
    }

}
