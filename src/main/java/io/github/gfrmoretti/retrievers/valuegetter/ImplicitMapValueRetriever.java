package io.github.gfrmoretti.retrievers.valuegetter;

import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.MapperConfig;
import io.github.gfrmoretti.annotations.repeatables.RepeatableImplicitMap;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

import static io.github.gfrmoretti.AnMap.map;
import static io.github.gfrmoretti.AnMap.mapReference;

@Slf4j
public class ImplicitMapValueRetriever extends ValueGetter {

    private final @Nullable("Optional, used to reference map.") Object target;
    @Nullable
    private ImplicitMap annotation;

    public ImplicitMapValueRetriever(Object source, Field annotatedField,
                                     Field sourceField, Field targetField, @Nullable Object target) {
        super(source, annotatedField, sourceField, targetField);
        this.target = target;
    }

    @Override
    protected Class<? extends Annotation> annotation() {
        return ImplicitMap.class;
    }

    @Override
    protected Class<? extends Annotation> repeatableAnnotation() {
        return RepeatableImplicitMap.class;
    }

    @Override
    protected MapperConfig getMapperConfigFromAnnotation() {
        return annotatedField.getAnnotation(ImplicitMap.class).mapperConfig();
    }

    @Override
    protected MapperConfig getMapperConfigFromRepeatableAnnotation(int index) {
        return annotatedField.getAnnotation(RepeatableImplicitMap.class).value()[index].mapperConfig();
    }

    @Override
    protected int getLengthRepeatableAnnotation() {
        return annotatedField.getAnnotation(RepeatableImplicitMap.class).value().length;
    }

    @Override
    protected void setRepeatableAnnotation(int index) {
        annotation = annotatedField.getAnnotation(RepeatableImplicitMap.class).value()[index];
    }

    @Override
    protected void setAnnotation() {
        annotation = annotatedField.getAnnotation(ImplicitMap.class);
    }


    @Override
    public Optional<Object> retrieve() {
        try {
            if (annotation == null)
                return Optional.empty();
            sourceField.setAccessible(true);
            var sourceValue = sourceField.get(source);

            if (sourceValue instanceof Collection<?>)
                return annotation.collectorType().collectValue(sourceValue, targetField, annotation.annotationSide());

            if (target != null) {
                targetField.setAccessible(true);
                mapReference(sourceField.get(source), targetField.get(target), annotation.annotationSide());
                return Optional.ofNullable(targetField.get(target));
            }

            return Optional.ofNullable(
                    map(sourceField.get(source), targetField.getType(), annotation.annotationSide())
                            .orElse(null));
        } catch (Exception e) {
            log.warn("Can not implicit map the field.");
        }
        return Optional.empty();
    }
}
