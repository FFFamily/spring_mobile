package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketConnectOptions;
import io.vertx.core.http.impl.headers.HeadersMultiMap;
import lombok.SneakyThrows;
import org.example.entity.Message;
import org.example.entity.MessageDto;
import org.example.entity.SocketResponse;
import org.example.enums.SocketResponseTypeEnum;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class client {
    private static String sessionId;

    public static void main(String[] args) {
        client1();
    }

    private static void client1() {
        Vertx vertx = Vertx.vertx();
        HttpClient client = vertx.createHttpClient();
        MultiMap entries = new HeadersMultiMap();
        entries.add("sessionId", "1");
        WebSocketConnectOptions options = new WebSocketConnectOptions()
                .setHost("127.0.0.1")
                .setPort(8080)
                .setHeaders(entries)
                .setAllowOriginHeader(false);
        AtomicReference<WebSocket> webSocket = new AtomicReference<>();
        client.webSocket(options, res -> {
            if (res.succeeded()) {
                System.out.println("Connected!");
                webSocket.set(res.result());
                webSocket.get().handler(content -> {
                    System.out.println("收到消息： " + content);
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        SocketResponse response = objectMapper.readValue(content.getBytes(), SocketResponse.class);
                        Long type = response.getType();
                        SocketResponseTypeEnum typeEnum = Arrays.stream(SocketResponseTypeEnum.values()).filter(item -> item.getValue().equals(type)).findFirst().orElse(null);
                        if (typeEnum != null) {
                            switch (typeEnum) {
                                // 登录成功
                                case LOGIN_SUCCESS -> sessionId = response.getSocketSessionId();
                                // 发送已读消息回调
                                case MESSAGE ->
                                        webSocket.get().writeBinaryMessage(Buffer.buffer(backMsg(response.getMessageId())));
                            }
                        } else {
                            throw new RuntimeException("客户端不支持的消费类型");
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                webSocket.get().writeBinaryMessage(Buffer.buffer(sendMsg("你好")));
            } else {
                System.out.println(res.result());
                System.out.println("fail");
            }
        });
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String msg = sc.nextLine();
            System.out.println("发送消息 : " + msg);
            webSocket.get().writeBinaryMessage(Buffer.buffer(sendMsg(msg)));
        }
    }

    @SneakyThrows
    private static String sendMsg(String msg) {
        MessageDto messageDto = new MessageDto();
        messageDto.setType(0L);
        Message message = new Message();
        message.setId(SocketHandler.uuid());
        message.setContent(msg);
        message.setSenderId("1");
        message.setReceiverId("2");
        messageDto.setMessage(message);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(messageDto);
    }

    @SneakyThrows
    private static String backMsg(String id) {
        MessageDto messageDto = new MessageDto();
        messageDto.setType(2L);
        Message message = new Message();
        message.setId(id);
        messageDto.setMessage(message);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(messageDto);
    }

}
