package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 가입 방법
 */
@Getter
@AllArgsConstructor
public enum EntryType {
    SELF("Self", "직접 가입"),
    COMPANY_ASSIGNED("Company-Assigned", "회사 관리자 생성"),
    ADMIN_ASSIGNED("Admin-Assigned", "최고 관리자 생성");

    private String value;
    private String description;

    @JsonCreator
    public static EntryType enumOf(String value) {
        return Arrays.stream(EntryType.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
