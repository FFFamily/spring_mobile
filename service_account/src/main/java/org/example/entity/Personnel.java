package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Personnel extends CommonEntity {
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 名称
    private String name;
    // 机构id
    private String orgId;
    // 认证类型
    private Integer roleType;
}
