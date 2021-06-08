package io.common.authorization.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 사업자 Type
 */
@Getter
@AllArgsConstructor
public enum BusinessType {
    SOLE("Sole","개인"),
    CORPORATION("Corporation","법인");

    private String value;
    private String description;

    @JsonCreator
    public static BusinessType enumOf(String value) {
        return Arrays.stream(BusinessType.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
