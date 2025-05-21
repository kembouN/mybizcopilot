package com.mybizcopilot.handlers;

import com.mybizcopilot.exception.ObjectValidationException;
import com.mybizcopilot.exception.OperationNonPermittedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Component
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({ObjectValidationException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(ObjectValidationException exception) {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .content(content)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getViolations().stream().toList().toString())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(EntityNotFoundException exception) {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(representation);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(NoSuchElementException exception) {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NO_CONTENT.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(representation);
    }

    @ExceptionHandler(OperationNonPermittedException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(OperationNonPermittedException exception) {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message(exception.getErrorMsg())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(DataIntegrityViolationException exception) {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(representation);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionRepresentation> handleDisabledException() {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message("Votre compte n'es pas activé")
                .status(HttpStatus.FORBIDDEN.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionRepresentation> handleBadCredentialsException() {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message("login ou mot de passe incorrect")
                .status(HttpStatus.FORBIDDEN.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ExceptionRepresentation> handleRestClientException(RestClientException restClientException) {
        logger.error("Erreur lors de la communication avec le service de mail: "+restClientException.getMessage());
        restClientException.printStackTrace();
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message("Erreur lors de la communication avec le service de mail: "+restClientException.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(representation);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionRepresentation> handleIllegalArgumentException(IllegalArgumentException exception) {
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .content(content)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(representation);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionRepresentation> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                //.map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                //.map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message(String.join(", ", errors))
                .status(HttpStatus.BAD_REQUEST.value())
                .content(new String[2])
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(representation);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionRepresentation> handleIOException(){
        String[] content = new String[1];
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .message("Erreur lors du chargement du fichier")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .content(content)
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(representation);
    }

}
