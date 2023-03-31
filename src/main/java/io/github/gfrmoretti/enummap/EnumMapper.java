package io.github.gfrmoretti.enummap;

import io.github.gfrmoretti.annotations.EnumMap;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class EnumMapper {

    protected final EnumMap annotation;
    protected final Object sourceValue;
    protected final Class<?> sourceClass;
    protected final Class<?> targetClass;

    public EnumMapper(EnumMap annotation, Object sourceValue, Class<?> sourceClass, Class<?> targetClass) {
        this.annotation = annotation;
        this.sourceValue = sourceValue;
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    private Optional<Object> findValueInEnum() {
        return Optional.ofNullable(
                Arrays.stream((targetClass).getEnumConstants())
                        .filter(predicate())
                        .findFirst()
                        .orElse(null)
        );
    }

    protected abstract Predicate<Object> predicate();

    protected abstract Optional<Object> getValueFromEnum() throws Exception;

    public Optional<Object> getValue() throws Exception {
        return sourceClass.isEnum() ? getValueFromEnum() : findValueInEnum();
    }
}
