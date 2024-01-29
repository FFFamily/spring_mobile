package org.example.entity;

import lombok.Data;

import java.util.List;
@Data
public class DemandMessage {
    private String id;
    // 需求Id
    private String demandId;
    // 保单id（订单id）
    private String policyId;
    // 发送者
    private String userId;
    // 接受者
    private String receiverId;
    // 是否顶置
    private Boolean isOverHead;
    // 是否删除(逻辑删除)
    private Boolean isDeleted;
    // 聊天的消息内容
//    private List<Message> messages;
}
