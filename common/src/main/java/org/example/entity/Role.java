package org.example.entity;

import java.util.List;

/**
 * 角色
 */
public class Role {
    // 角色id
    private Long id;
    // 角色名称
    private String name;
    // 权限
    private List<Permission> permissions;
}
