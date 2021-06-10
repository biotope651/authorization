package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.Depth;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class DepthConverter implements AttributeConverter<Depth, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Depth depth) {
        if (depth == null) return null;
        return depth.getValue();
    }

    @Override
    public Depth convertToEntityAttribute(Integer value) {
        if (value == null) return null;

        return Stream.of(Depth.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
