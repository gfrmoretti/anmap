package io.github.gfrmoretti.retrievers.valuegetter;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.MapperConfig;
import io.github.gfrmoretti.annotations.repeatables.RepeatableDateMap;
import io.github.gfrmoretti.datemap.StringToTemporalMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Slf4j
class DateValueRetriever extends ValueGetter {

    @Nullable
    private DateMap annotation;

    public DateValueRetriever(Object source, Field annotatedField, Field sourceField, Field targetField) {
        super(source, annotatedField, sourceField, targetField);
    }

    @Override
    protected Class<? extends Annotation> annotation() {
        return DateMap.class;
    }

    @Override
    protected Class<? extends Annotation> repeatableAnnotation() {
        return RepeatableDateMap.class;
    }

    @Override
    protected MapperConfig getMapperConfigFromAnnotation() {
        return annotatedField.getAnnotation(DateMap.class).mapperConfig();
    }

    @Override
    protected MapperConfig getMapperConfigFromRepeatableAnnotation(int index) {
        return annotatedField.getAnnotation(RepeatableDateMap.class).value()[index].mapperConfig();
    }

    @Override
    protected int getLengthRepeatableAnnotation() {
        return annotatedField.getAnnotation(RepeatableDateMap.class).value().length;
    }

    @Override
    protected void setRepeatableAnnotation(int index) {
        annotation = annotatedField.getAnnotation(RepeatableDateMap.class).value()[index];
    }

    @Override
    protected void setAnnotation() {
        annotation = annotatedField.getAnnotation(DateMap.class);
    }

    @Override
    public Optional<Object> retrieve() {
        try {
            if (annotation == null)
                return Optional.empty();

            var formatPattern = annotation.formatPattern();
            sourceField.setAccessible(true);
            var sourceValue = sourceField.get(source);

            if (typeObjectIsNotExpected(sourceValue))
                return Optional.empty();

            if (sourceValue instanceof String) {
                var temporalClass = targetField.getType();
                var function = new StringToTemporalMapper(temporalClass, formatPattern);
                return Optional.ofNullable(function.mapToTemporal((String) sourceValue).orElse(null));
            }
            if (sourceValue instanceof TemporalAccessor) {
                var temporalClass = sourceField.getType();
                var function = new StringToTemporalMapper(temporalClass, formatPattern);
                return Optional.ofNullable(function.mapToString((TemporalAccessor) sourceValue).orElse(null));
            }

        } catch (Exception e) {
            log.warn("Problem to retrieve date value from the function on field");
        }
        return Optional.empty();
    }

    private boolean typeObjectIsNotExpected(Object obj) {
        try {
            if (((TemporalAccessor) obj).getClass() != null)
                return false;
        } catch (Throwable e) {
            return !(obj instanceof String);
        }
        return true;
    }
}
