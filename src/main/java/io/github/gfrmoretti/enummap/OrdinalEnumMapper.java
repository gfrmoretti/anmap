package io.github.gfrmoretti.enummap;

import io.github.gfrmoretti.annotations.EnumMap;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Predicate;

public class OrdinalEnumMapper extends EnumMapper {

    public OrdinalEnumMapper(EnumMap annotation, Object sourceValue, Class<?> sourceClass, Class<?> targetClass) {
        super(annotation, sourceValue, sourceClass, targetClass);
    }

    @Override
    public Predicate<Object> predicate() {
        return anEnum -> {
            try {
                return annotation.mustMapOrdinalToInt() ?
                        targetClass.getMethod("ordinal").invoke(anEnum).equals(sourceValue) :
                        String.valueOf(targetClass.getMethod("ordinal").invoke(anEnum)).equals(sourceValue);
            } catch (IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public Optional<Object> getValueFromEnum() throws Exception {
        return annotation.mustMapOrdinalToInt() ?
                Optional.ofNullable(sourceClass.getMethod("ordinal").invoke(sourceValue)) :
                Optional.ofNullable(String.valueOf(sourceClass.getMethod("ordinal").invoke(sourceValue)));
    }
}
