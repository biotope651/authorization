package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.UserType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType userType) {
        if (userType == null) return null;
        return userType.getValue();
    }

    @Override
    public UserType convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(UserType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
