package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.CollaborateurRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.CollaborateurResponse;
import com.mybizcopilot.services.ICollaborateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/collaborateur")
public class CollaborateurController {

    private ICollaborateurService collaborateurService;


    @PostMapping
    public ResponseEntity<BaseResponse<Void>> addCollab( @RequestBody CollaborateurRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Collaborateur Ajouté",
                        collaborateurService.ajouterCollaborateur(request)
                )
        );
    }

    @GetMapping("/get-by/entreprise/{idEntreprise}")
    public ResponseEntity<BaseResponse<List<CollaborateurResponse>>> getAllCollab(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        collaborateurService.getAllCollaborateurs(idEntreprise)
                )
        );
    }

    @PutMapping("/{idCollaborateur}")
    public ResponseEntity<BaseResponse<Void>> updateCollab(@PathVariable Integer idCollaborateur, @RequestBody CollaborateurRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Collaborateur modifié",
                        collaborateurService.updateCollaborateur(idCollaborateur, request)
                )
        );
    }

}
