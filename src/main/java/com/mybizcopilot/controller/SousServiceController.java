package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.SousServiceRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.dto.responses.SousServiceResponse;
import com.mybizcopilot.services.ISousServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sous-service")
public class SousServiceController {

    private ISousServiceService sousServiceService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> ajouterSousService(@RequestBody SousServiceRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Sous service ajouté",
                        sousServiceService.ajouterSousService(request)
                )
        );
    }


    @GetMapping("/get-by/enterprise/{idEntreprise}")
    public ResponseEntity<BaseResponse<List<SousServiceResponse>>> getAllSousService(@PathVariable Integer idEntreprise) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        sousServiceService.getAllSousServiceByEnterprise(idEntreprise)
                )
        );
    }

    @PutMapping("/{idSousservice}")
    public ResponseEntity<BaseResponse<Void>> updateSousService(@PathVariable Integer idSousservice, @RequestBody SousServiceRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Modification effectuée",
                        sousServiceService.updateSousService(idSousservice, request)
                )
        );
    }
}
