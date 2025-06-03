package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.CommandeRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.CommandeResponse;
import com.mybizcopilot.services.ICommandeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/commande")
public class CommandeController {

    private ICommandeService commandeService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> ajouterService(@RequestBody CommandeRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Commande ajouté",
                        commandeService.ajouterCommande(request)
                )
        );
    }

    @GetMapping("/{idCommande}")
    public ResponseEntity<BaseResponse<CommandeResponse>> getCommande(@PathVariable Integer idCommande) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Commande récupéré",
                        commandeService.afficherCommande(idCommande)
                )
        );
    }

    @GetMapping("/get-by/entreprise/{idEntreprise}")
    public ResponseEntity<BaseResponse<List<CommandeResponse>>> getAllCommandes(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Service récupéré",
                        commandeService.listeCommandes(idEntreprise)
                )
        );
    }

    @PutMapping("/{idCommande}")
    public ResponseEntity<BaseResponse<Void>> updateCommande(@PathVariable Integer idCommande,@RequestBody CommandeRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Commande modifée",
                        commandeService.updateCommande(idCommande, request)
                )
        );
    }

}
