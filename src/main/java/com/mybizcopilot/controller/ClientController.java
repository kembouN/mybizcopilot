package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.ClientRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.ClientResponse;
import com.mybizcopilot.services.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Client")
@RequestMapping("/api/v1/client")
public class ClientController {

    private IClientService clientService;

    @PostMapping
    @Operation(description = "Ajouter un client à l'entreprise")
    public ResponseEntity<BaseResponse<Void>> ajouterClient(@RequestBody ClientRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Client ajouté",
                        clientService.ajouterClient(request)
                )
        );
    }

    @GetMapping("/{idClient}")
    @Operation(description = "Afficher les information sur un client")
    public ResponseEntity<BaseResponse<ClientResponse>> getClient(@PathVariable Integer idClient) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        clientService.getClient(idClient)
                )
        );
    }


    @GetMapping("/get-by/entreprise/{idEntreprise}")
    @Operation(description = "Obtenir la liste des clients de l'entreprise")
    public ResponseEntity<BaseResponse<List<ClientResponse>>> getAllByEntreprise(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        clientService.getAllClient(idEntreprise)
                )
        );
    }

    @PutMapping("/{idClient}")
    @Operation(description = "Mettre à jour les informations d'un client")
    public ResponseEntity<BaseResponse<ClientResponse>> update(@PathVariable Integer idClient, @RequestBody ClientRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Client mis à jour",
                        clientService.updateClient(idClient, request)
                )
        );
    }

    @GetMapping
    @Operation(description = "obtenir tous les client enregistrés")
    public ResponseEntity<BaseResponse<List<ClientResponse>>> getAllClient(){
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        clientService.getAllClients()
                )
        );
    }
}
