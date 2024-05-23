package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountTypeEnum implements DefaultEnum<Integer> {
    ORG(0),
    PERSONNEL(1);
    private final Integer code;
}
