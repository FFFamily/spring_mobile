package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SocketResponseTypeEnum {
    LOGIN_SUCCESS(0L), // 首次绑定连接成功
    MESSAGE(1L), // 消息
    ;
    private final Long value;
}
