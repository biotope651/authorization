package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.Depth;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class DepthConverter implements AttributeConverter<Depth, String> {
    @Override
    public String convertToDatabaseColumn(Depth depth) {
        if (depth == null) return null;
        return depth.getValue();
    }

    @Override
    public Depth convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(Depth.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
