package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {
    TEXT(1L),// 文本消息
    IMG(2L), // 图片
    FILE(3L),// 文件
    ;
    private final Long value;
}
