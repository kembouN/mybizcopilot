package com.mybizcopilot.controller;

import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.entities.Tranche;
import com.mybizcopilot.entities.Typeprospect;
import com.mybizcopilot.services.ITrancheService;
import com.mybizcopilot.services.ITypeprospectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class TrancheEtTypeProspectController {

    private ITypeprospectService typeprospectService;

    private ITrancheService trancheService;


    @GetMapping("type-prospect")
    @Operation(description = "Obtenir la liste des types de prospect")
    @Tag(name = "Type Prospect")
    public ResponseEntity<BaseResponse<List<Typeprospect>>> getAll(){
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        typeprospectService.getAllTypeprospect()
                )
        );
    }

    @GetMapping("tranche-age")
    @Operation(description = "Obtenir la liste des tranche d'age pour clients")
    @Tag(name = "Tranche d'âge")
    public ResponseEntity<BaseResponse<List<Tranche>>> getAllTranche(){
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "",
                        trancheService.getAllTranches()
                )
        );
    }

}
