package org.example.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.config.MySocket;
import org.example.entity.CommonResponse;
import org.example.entity.SocketSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
@Slf4j
@CrossOrigin(origins = "*")
public class MessageController {

    @SneakyThrows
    @GetMapping("/findAllChat/{sessionId}")
    public CommonResponse<List<SocketSession>> createPolicy(@PathVariable String sessionId) {
//        Thread.sleep(1000);
        return CommonResponse.success(MySocket.SocketSessionDataBase.stream().filter(item -> !item.getId().equals(sessionId)).collect(Collectors.toList()));
    }

    @GetMapping("/getOneChatInfo/{senderId}/{receiverId}")
    public CommonResponse<List> createPolicy(@PathVariable String senderId, @PathVariable String receiverId) {
        return CommonResponse.success(
                MySocket.messageDataBase.stream()
                        .filter(item -> senderId.equals(item.getSenderId()) || receiverId.equals(item.getSenderId()))
                        .sorted((a, b) -> Math.toIntExact(a.getCreatedAt() - b.getCreatedAt()))
                        .collect(Collectors.toList())
        );
    }
}
