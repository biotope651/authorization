package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 회원 상태
 */
@Getter
@AllArgsConstructor
public enum UserStatus {
    REQUEST("Request", "회원가입 요청"),
    SHELVE("Shelve", "회원가입 보류"),
    DONE("Enable", "회원가입 완료"),
    DISABLE("Disable", "회원가입 비활성화"),
    DELETED("Deleted", "회원 탈퇴");

    private String value;
    private String description;

    @JsonCreator
    public static UserStatus enumOf(String value) {
        return Arrays.stream(UserStatus.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
