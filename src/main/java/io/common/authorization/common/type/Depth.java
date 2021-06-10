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
    FIRST(1,"First"),
    SECOND(2, "Second");

    private Integer value;
    private String description;

    @JsonCreator
    public static Depth enumOf(Integer value) {
        return Arrays.stream(Depth.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }
}
