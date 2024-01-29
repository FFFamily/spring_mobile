package org.example.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;
import lombok.SneakyThrows;
import org.example.entity.Message;
import org.example.entity.MessageDto;
import org.example.entity.SocketResponse;
import org.example.entity.SocketSession;
import org.example.enums.SocketResponseTypeEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SocketHandler implements Handler<ServerWebSocket> {
    // 以下模拟数据库操作
    public static List<SocketSession> SocketSessionDataBase = new ArrayList<>();
    // 消息数据库
    public static List<Message> messageDataBase = new ArrayList<>();
    private static HashMap<String, String> sessionDataBase = new HashMap<>();

    static {
        sessionDataBase.put("1", "1");
        sessionDataBase.put("2", "2");
        sessionDataBase.put("3", "3");
    }

    private final HashMap<String, ServerWebSocket> socketHashMap = new HashMap<>();
    private HashMap<String, String> map = new HashMap<>();

    public static String uuid() {
        return java.util.UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    @Override
    public void handle(ServerWebSocket webSocket) {
        webSocket.accept();
        System.out.println("有人建立连接啦");
        String sessionId = webSocket.query().split("=")[1];
        if (sessionId == null) {
            throw new RuntimeException("位置用户的Socket连接");
        }
        // TODO 未来待删除代码
        String userId = sessionId;
        // 获取每一个链接的ID
        String binaryHandlerID = webSocket.binaryHandlerID();
        // 绑定当前用户的 socketID
        map.put(userId, binaryHandlerID);
        // 绑定Id
        socketHashMap.putIfAbsent(binaryHandlerID, webSocket);
        // 存储 webSocketSession
        SocketSession socketSession = new SocketSession();
        socketSession.setId(sessionId);
        socketSession.setUserId(userId);
        socketSession.setBinaryHandlerID(binaryHandlerID);
        if (SocketSessionDataBase.stream().anyMatch(item -> item.getUserId().equals(userId))) {
            // 说明session还在
            SocketSession oldSocketSession = SocketSessionDataBase.stream().filter(item -> item.getUserId().equals(userId)).findFirst().get();
            SocketSessionDataBase.remove(oldSocketSession);
            SocketSessionDataBase.add(socketSession);
        } else {
            SocketSessionDataBase.add(socketSession);
        }
        // 登录成功回调
        loginSuccess(webSocket, socketSession.getId());
        System.out.println("当前登录的人员有: " + SocketSessionDataBase.stream().map(SocketSession::getUserId).collect(Collectors.joining("、")));
        webSocket.binaryMessageHandler(content -> {

        });

        webSocket.closeHandler(handle -> {
            // 移除session
            SocketSession session = SocketSessionDataBase.stream().filter(item -> item.getId().equals(sessionId)).findFirst().orElse(null);
            if (session == null) {
                System.out.println("不存在对应session");
            }
            SocketSessionDataBase.remove(session);
            System.out.println("已将对应用户移出" + sessionId);
//            System.out.println("当前登录的人员有: " + SocketSessionDataBase.stream().map(SocketSession::getUserId).collect(Collectors.joining("、")));
        });

        webSocket.textMessageHandler(content -> {
            System.out.println("处理消息：" + content);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                MessageDto messageDto = objectMapper.readValue(content.getBytes(), MessageDto.class);
                Long type = messageDto.getType();
                Message message = messageDto.getMessage();
                String socketSessionId = messageDto.getSocketSessionId();
                if (checkSocketSession(socketSessionId)) {
                    throw new RuntimeException("缺失socketSession，请重新建立Socket连接");
                }
                if (type == 0L) {
                    System.out.println("发送人 " + message.getSenderId() + " 接收人 " + message.getReceiverId());
                    // 发送消息
                    // 拿到消息接收者
                    String receiverId = message.getReceiverId();
                    // 拿到对应的 websocket id
                    String webSocketId = map.get(receiverId);
                    if (webSocketId != null) {
                        ServerWebSocket serverWebSocket = socketHashMap.get(webSocketId);
                        sendMessage(serverWebSocket, message.getContent());
//                        serverWebSocket.writeBinaryMessage(Buffer.buffer(message.getContent()));
                    } else {
                        System.out.println("对方不在线，直接进入数据库流程");
                        // 证明对方不在线
                        // 要么直接更新数据库|要么等待其退出
                    }
                    // 然后发送MQ消息保存信息
                    // 只有通过mq消费之后才会是真正的发送成功
                    message.setSendSuccess(false);
                    message.setCreatedAt(new Date().getTime());
                    messageDataBase.add(message);
                } else if (type == 1L) {
                    // 拿到进入的哪个聊天框
                    String demandMessageId = message.getDemandMessageId();
                    // 进入聊天
                    // 进入聊天列表页面然后点击某个人
                    // 那么就会将未读的消息更新为已读
                    messageDataBase.forEach(item -> {
                        if (item.getDemandMessageId().equals(demandMessageId) && item.getSenderId().equals(message.getSenderId())) {
                            item.setRead(true);
                        }
                    });
                    // 倘若对方在线且在同一俩天内 同时要告诉对方自己已读
                } else if (type == 2L) {
                    // 消息已读回调
                    setMessageIsRead(message.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    private void setMessageIsRead(String id) {
        messageDataBase.forEach(item -> {
            if (item.getId().equals(id)) {
                item.setRead(true);
            }
        });
    }

    private boolean checkSocketSession(String socketSessionId) {
        return SocketSessionDataBase.stream().anyMatch(item -> item.getId().equals(socketSessionId));
    }

    @SneakyThrows
    private void loginSuccess(ServerWebSocket webSocket, String socketSessionId) {
        SocketResponse socketResponse = new SocketResponse();
        socketResponse.setType(SocketResponseTypeEnum.LOGIN_SUCCESS.getValue());
        socketResponse.setSocketSessionId(socketSessionId);
        ObjectMapper objectMapper = new ObjectMapper();
//        webSocket.writeBinaryMessage(Buffer.buffer(objectMapper.writeValueAsString(socketResponse)));
        webSocket.writeTextMessage(objectMapper.writeValueAsString(socketResponse));
    }

    @SneakyThrows
    private void sendMessage(ServerWebSocket webSocket, String content) {
        SocketResponse socketResponse = new SocketResponse();
        socketResponse.setType(SocketResponseTypeEnum.MESSAGE.getValue());
        Message message = new Message();
        message.setContent(content);
        socketResponse.setMessage(message);
        ObjectMapper objectMapper = new ObjectMapper();
//        webSocket.writeBinaryMessage(Buffer.buffer(objectMapper.writeValueAsString(socketResponse)));
        webSocket.writeTextMessage(objectMapper.writeValueAsString(socketResponse));
    }
}
