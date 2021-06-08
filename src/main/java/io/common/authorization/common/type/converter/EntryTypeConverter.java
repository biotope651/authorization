package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.EntryType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class EntryTypeConverter implements AttributeConverter<EntryType, String> {

    @Override
    public String convertToDatabaseColumn(EntryType entryType) {
        if (entryType == null) return null;
        return entryType.getValue();
    }

    @Override
    public EntryType convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(EntryType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
