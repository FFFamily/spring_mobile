package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum {
    // 普通用户（没有认证）
    USER(0),
    // 超级用户（已认证）
    SUPER_USER(1),
    // G
    ADMIN(1);
    private final Integer code;

}
