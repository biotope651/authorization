package io.common.authorization.common.type.converter;

import io.common.authorization.common.type.AnswerType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class AnswerTypeConverter implements AttributeConverter<AnswerType, String> {

    @Override
    public String convertToDatabaseColumn(AnswerType answerType) {
        if (answerType == null) return null;
        return answerType.getValue();
    }

    @Override
    public AnswerType convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(AnswerType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
