package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.ActiveStatus;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class ActiveStatusConverter implements AttributeConverter<ActiveStatus, String> {

    @Override
    public String convertToDatabaseColumn(ActiveStatus activeStatus) {
        if (activeStatus == null) return null;
        return activeStatus.getValue();
    }

    @Override
    public ActiveStatus convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(ActiveStatus.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
