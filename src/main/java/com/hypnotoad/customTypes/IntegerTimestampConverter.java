package com.hypnotoad.customTypes;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Converter
public class IntegerTimestampConverter implements AttributeConverter<LocalDateTime, Long> {
    @Override
    public Long convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long aLong) {
        return LocalDateTime.ofEpochSecond(aLong, 0, ZoneOffset.UTC);
    }
}
