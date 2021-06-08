package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Menu Depth Type
 */
@Getter
@AllArgsConstructor
public enum Depth {
    FIRST("First","First"),
    SECOND("Second", "Second");

    private String value;
    private String description;

    @JsonCreator
    public static Depth enumOf(String value) {
        return Arrays.stream(Depth.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
