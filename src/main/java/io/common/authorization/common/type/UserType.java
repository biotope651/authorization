package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 사용자 타입
 */
@Getter
@AllArgsConstructor
public enum UserType {
    NOMAL("Nomal","일반 사용자"),
    COMPANY("Company", "회사 사용자"),
    COMPANY_ADMIN("Company-Admin", "회사 관리자"),
    SUPER_ADMIN("Super-Admin","제공사 최고 관리자");

    private String value;
    private String description;

    @JsonCreator
    public static UserType enumOf(String value) {
        return Arrays.stream(UserType.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
