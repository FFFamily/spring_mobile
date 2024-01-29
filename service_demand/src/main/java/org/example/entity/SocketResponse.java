package org.example.entity;

import lombok.Data;

@Data
public class SocketResponse {
    private Long Type;
    private String messageId;
    private String socketSessionId;
    private Message message;
}
