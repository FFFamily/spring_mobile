package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Org extends Account {
    // 机构名称
    private String name;
    // 上级机构id
    private String supOrgId;
    // account id
    private String accountId;
}
