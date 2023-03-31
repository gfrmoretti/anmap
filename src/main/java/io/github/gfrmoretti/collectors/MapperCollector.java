package io.github.gfrmoretti.collectors;

import io.github.gfrmoretti.conf.AnnotationSide;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.stream.Stream;

public interface MapperCollector {

    Optional<Object> collectValue(Object sourceValue, Field targetField, AnnotationSide side) throws Exception;

    Optional<Object> collectFromStream(Stream<?> stream);

    default Class<?> getClassFromCollection(Field targetField) throws ClassNotFoundException {
        return Class.forName(((ParameterizedType) targetField.getGenericType())
                .getActualTypeArguments()[0].getTypeName());
    }
}
