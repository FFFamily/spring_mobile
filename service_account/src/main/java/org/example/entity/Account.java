package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

// 业务员
@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends CommonEntity {
    // 机构|用户 的用户名
    private String userName;
    // 机构|用户 id
    private String userId;
    // 密码
    private String password;
}
