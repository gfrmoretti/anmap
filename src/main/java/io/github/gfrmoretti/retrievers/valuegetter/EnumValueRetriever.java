package io.github.gfrmoretti.retrievers.valuegetter;

import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.annotations.MapperConfig;
import io.github.gfrmoretti.annotations.repeatables.RepeatableEnumMap;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

@Slf4j
class EnumValueRetriever extends ValueGetter {

    @Nullable
    private EnumMap annotation;

    public EnumValueRetriever(Object source, Field annotatedField, Field sourceField, Field targetField) {
        super(source, annotatedField, sourceField, targetField);
    }

    @Override
    protected Class<? extends Annotation> annotation() {
        return EnumMap.class;
    }

    @Override
    protected Class<? extends Annotation> repeatableAnnotation() {
        return RepeatableEnumMap.class;
    }

    @Override
    protected MapperConfig getMapperConfigFromAnnotation() {
        return annotatedField.getAnnotation(EnumMap.class).mapperConfig();
    }

    @Override
    protected MapperConfig getMapperConfigFromRepeatableAnnotation(int index) {
        return annotatedField.getAnnotation(RepeatableEnumMap.class).value()[index].mapperConfig();
    }

    @Override
    protected int getLengthRepeatableAnnotation() {
        return annotatedField.getAnnotation(RepeatableEnumMap.class).value().length;
    }

    @Override
    protected void setRepeatableAnnotation(int index) {
        annotation = annotatedField.getAnnotation(RepeatableEnumMap.class).value()[index];
    }

    @Override
    protected void setAnnotation() {
        annotation = annotatedField.getAnnotation(EnumMap.class);
    }

    @Override
    public Optional<Object> retrieve() {
        try {
            if (annotation == null)
                return Optional.empty();
            return selectRetrieve(annotation);
        } catch (Exception e) {
            log.warn("Problem to retrieve value from the enum.");
        }
        return Optional.empty();
    }

    private Optional<Object> selectRetrieve(EnumMap annotation) throws Exception {
        sourceField.setAccessible(true);
        var sourceValue = sourceField.get(source);

        var enumMapping = annotation.enumMapType()
                .getEnumMapping(annotation, sourceValue, sourceField.getType(), targetField.getType());
        return enumMapping.getValue();
    }
}
