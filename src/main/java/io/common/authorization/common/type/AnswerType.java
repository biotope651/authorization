package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * answer type
 * Y,N
 */
@Getter
@AllArgsConstructor
public enum AnswerType {
    Y("Y","네"),
    N("N", "아니오");

    private String value;
    private String description;

    @JsonCreator
    public static AnswerType enumOf(String value) {
        return Arrays.stream(AnswerType.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
