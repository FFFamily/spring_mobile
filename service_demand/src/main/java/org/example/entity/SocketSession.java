package org.example.entity;

import lombok.Data;

@Data
public class SocketSession {
    private String id;
    private String userId;
    private String binaryHandlerID;
}
