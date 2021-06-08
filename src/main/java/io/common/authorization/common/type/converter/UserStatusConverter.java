package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.UserStatus;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) return null;
        return userStatus.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(UserStatus.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
