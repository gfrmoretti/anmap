package io.github.gfrmoretti.collectors;

import io.github.gfrmoretti.conf.AnnotationSide;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
public enum CollectorType {
    LIST(new ListCollector()),
    SET(new SetCollector());
    private final MapperCollector mapperCollector;

    public Optional<Object> collectValue(Object sourceValue, Field targetField, AnnotationSide side) throws Exception {
        return mapperCollector.collectValue(sourceValue, targetField, side);
    }

    public Optional<Object> collectFromStream(Stream<?> stream) {
        return mapperCollector.collectFromStream(stream);
    }
}
