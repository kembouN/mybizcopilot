package com.mybizcopilot.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class SendMessageRequest {

    @Size(max = 4096, message = "Votre message dépasse la taille maximale autorisée")
    @NotEmpty(message = "Veuillez entrer un message")
    private String message;

    private File file;

    @Size(max = 1024, message = "Votre légende dépasse la taille maximale autorisée")
    private String caption;

    @Size(max = 255, message = "Le nom du fichier dépasse la taille maximale autorisée")
    private String fileName;

    @NotNull(message = "Aucun destinataire trouvé")
    private List<Integer> receivers;

}
