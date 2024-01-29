package org.example.entity;

import lombok.Data;

// 消息传输对象
@Data
public class MessageDto {
    private Message message;
    private Long type;
    private String socketSessionId;
}
