package io.github.gfrmoretti.enummap;

import io.github.gfrmoretti.annotations.EnumMap;

import java.util.Optional;
import java.util.function.Predicate;

public class StringEnumMapper extends EnumMapper {

    public StringEnumMapper(EnumMap annotation, Object sourceValue, Class<?> sourceClass, Class<?> targetClass) {
        super(annotation, sourceValue, sourceClass, targetClass);
    }

    @Override
    public Predicate<Object> predicate() {
        return anEnum -> anEnum.toString().equals(sourceValue);
    }

    public Optional<Object> getValueFromEnum() {
        return Optional.ofNullable(sourceValue.toString());
    }
}
