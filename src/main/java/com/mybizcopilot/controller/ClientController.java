package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.ClientRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.ClientResponse;
import com.mybizcopilot.entities.Pays;
import com.mybizcopilot.services.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Client")
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping("/client")
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

    @GetMapping("/client/{idClient}")
    @Operation(description = "Afficher les informations sur un client")
    public ResponseEntity<BaseResponse<ClientResponse>> getClient(@PathVariable Integer idClient) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        clientService.getClient(idClient)
                )
        );
    }


    @GetMapping("/client/get-by/entreprise/{idEntreprise}")
    @Operation(description = "Obtenir la liste des clients de l'entreprise")
    public ResponseEntity<BaseResponse<List<ClientResponse>>> getAllByEntreprise(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Liste des clients de l'entreprise",
                        clientService.getAllClient(idEntreprise)
                )
        );
    }

    @PutMapping("/client/{idClient}")
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

    @GetMapping("/client")
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

    @GetMapping("/pays")
    @Operation(description = "Obtenir la liste des pays")
    public ResponseEntity<BaseResponse<List<Pays>>> getAllPays() {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        clientService.getAllPays()
                )
        );
    }
}
