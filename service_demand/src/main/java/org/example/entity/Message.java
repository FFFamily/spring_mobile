package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    // 消息id
    private String id;
    // 所属聊天内容Id
    private String DemandMessageId;
    // 消息类型
    private Integer type;
    // 发送者id
    private String senderId;
    // 接收者id
    private String receiverId;
    // 是否已读
    private boolean isRead;
    // 是否发送成功
    private boolean isSendSuccess;
    // 消息内容
    private String content;
    // 创建时间
    private Long createdAt;
}
