package org.example.entity;

import lombok.Data;

/**
 * 权限
 */
@Data
public class Permission {
    // 编号
    private Long id;
    // 权限名称
    private String name;
    // 权限描述
    private String desc;
}
