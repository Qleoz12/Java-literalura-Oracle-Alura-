package io.qleoz12.com.alura.literalura.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year year) {
        return (year != null) ? year.getValue() : null;
    }

    @Override
    public Year convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? Year.of(dbData) : null;
    }
}