package io.github.gfrmoretti.collectors;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.conf.AnnotationSide;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListCollector implements MapperCollector {
    @Override
    public Optional<Object> collectValue(Object sourceValue, Field targetField, AnnotationSide side) throws Exception {
        return Optional.of(
                AnMap.mapCollection((Collection<?>) sourceValue, getClassFromCollection(targetField), side)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Object> collectFromStream(Stream<?> stream) {
        if (stream == null)
            return Optional.empty();
        return Optional.of(stream.collect(Collectors.toList()));
    }
}
