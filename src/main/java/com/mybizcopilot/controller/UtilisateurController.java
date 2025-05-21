package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.ChangePasswordRequest;
import com.mybizcopilot.dto.requests.LoginRequest;
import com.mybizcopilot.dto.requests.RegisterRequest;
import com.mybizcopilot.dto.responses.LoginResponse;
import com.mybizcopilot.services.IUtilisateurService;
import com.mybizcopilot.dto.responses.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user/")
@Tag(name = "Utilisateur")
public class UtilisateurController {

    private IUtilisateurService utilisateurService;

    @PostMapping("register")
    @Operation(description = "Créer un compte en tant que responsable d'entreprise")
    public ResponseEntity<BaseResponse<?>> createAccount(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Compte créé",
                        utilisateurService.register(request)
                )
        );
    }

    @PostMapping("login")
    @Operation(description = "Connexion au compte utilisateur")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Connecté",
                        utilisateurService.login(request)
                )
        );
    }

    @PutMapping("{idUser}/password-update")
    @Operation(description = "Modifier le mot de passe utilisateur")
    public ResponseEntity<BaseResponse<Void>> changePass(@PathVariable Integer idUser, @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Mot de passe modifié",
                        utilisateurService.changePassword(idUser, request)
                )
        );
    }

}
