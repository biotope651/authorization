package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.BusinessType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class BusinessTypeConverter implements AttributeConverter<BusinessType, String> {

    @Override
    public String convertToDatabaseColumn(BusinessType businessType) {
        if (businessType == null) return null;
        return businessType.getValue();
    }

    @Override
    public BusinessType convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(BusinessType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
