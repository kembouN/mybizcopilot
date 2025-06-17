package com.mybizcopilot.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@Builder
@Getter
@Setter
public class Messages extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client receiver;

    private String message;

    private byte[] file;

    private String fileName;

    private String caption;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private StatutMessage statutMsg;

}
