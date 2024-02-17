package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class Org extends CommonEntity {
    // 机构名称
    private String name;
    // 用户名
    private String userName;
    // 上级机构id
    private String supOrgId;
}
