package com.mybizcopilot.controller;

import com.mybizcopilot.dto.requests.SendMessageRequest;
import com.mybizcopilot.dto.responses.BaseResponse;
import com.mybizcopilot.services.IMessagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
@Tag(name = "Messages")
public class MessageController {

    @Autowired
    private IMessagesService messagesService;

    @PostMapping("/text")
    @Operation(description = "Envoyez un message texte")
    public ResponseEntity<BaseResponse<Void>> sendWhatsAppText(@RequestBody SendMessageRequest request) {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        HttpStatus.OK.value(),
                        "Message envoyé",
                        messagesService.sendTextMessage(request)
                )
        );
    }
}
