package com.mybizcopilot.dto.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class MessageResponse {

    private Integer messageId;

    private String message;

    private byte[] file;
}
