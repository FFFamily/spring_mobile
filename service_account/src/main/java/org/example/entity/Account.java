package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

// 业务员
@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends CommonEntity {
    // 机构|用户 的用户名
    private String userName;
    // 密码
    private String password;
    // account 类型： 机构|人员
    private Integer accountType;
}
