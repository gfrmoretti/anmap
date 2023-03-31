package io.github.gfrmoretti.retrievers.valuegetter;

import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.MapperConfig;
import io.github.gfrmoretti.annotations.repeatables.RepeatableFunctionMap;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static io.github.gfrmoretti.ReflectionUtils.getMethodThatParameterMatchesWithClass;

@Slf4j
class FunctionValueRetriever extends ValueGetter {
    @Nullable
    private FunctionMap annotation;

    public FunctionValueRetriever(Object source, Field annotatedField, Field sourceField, Field targetField) {
        super(source, annotatedField, sourceField, targetField);
    }

    @Override
    protected Class<? extends Annotation> annotation() {
        return FunctionMap.class;
    }

    @Override
    protected Class<? extends Annotation> repeatableAnnotation() {
        return RepeatableFunctionMap.class;
    }

    @Override
    protected MapperConfig getMapperConfigFromAnnotation() {
        return annotatedField.getAnnotation(FunctionMap.class).mapperConfig();
    }

    @Override
    protected MapperConfig getMapperConfigFromRepeatableAnnotation(int index) {
        return annotatedField.getAnnotation(RepeatableFunctionMap.class).value()[index].mapperConfig();
    }

    @Override
    protected int getLengthRepeatableAnnotation() {
        return annotatedField.getAnnotation(RepeatableFunctionMap.class).value().length;
    }

    @Override
    protected void setRepeatableAnnotation(int index) {
        annotation = annotatedField.getAnnotation(RepeatableFunctionMap.class).value()[index];
    }

    @Override
    protected void setAnnotation() {
        annotation = annotatedField.getAnnotation(FunctionMap.class);
    }

    @Override
    public Optional<Object> retrieve() {
        try {
            if (annotation == null)
                return Optional.empty();

            sourceField.setAccessible(true);
            var sourceValue = sourceField.get(source);

            if (sourceValue instanceof Collection<?>)
                return retrieveFromCollectionTypes((Collection<?>) sourceValue);

            var function = annotation.value().getDeclaredConstructor().newInstance();
            var method = getMethodThatParameterMatchesWithClass(annotation.value(), sourceField.getType());
            var obj = ((Optional<?>) method.invoke(function, sourceValue)).orElse(null);

            return Optional.ofNullable(obj);
        } catch (Exception e) {
            log.warn("Problem to retrieve value from the function on field");
        }
        return Optional.empty();
    }

    private Optional<Object> retrieveFromCollectionTypes(Collection<?> collectionSource) throws Exception {
        if (annotation == null)
            return Optional.empty();

        var function = annotation.value().getDeclaredConstructor().newInstance();

        var temporaryList = new ArrayList<>();
        for (Object o : collectionSource) {
            var method = getMethodThatParameterMatchesWithClass(annotation.value(), o.getClass());
            var valueConverted = (Optional<?>) method.invoke(function, o);
            temporaryList.add(valueConverted.orElseThrow());
        }
        return annotation.collector().collectFromStream(temporaryList.stream());
    }
}
