package io.github.gfrmoretti.retrievers.targetfield;

import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.annotations.repeatables.RepeatableMap;
import io.github.gfrmoretti.retrievers.Retriever;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Optional;

import static io.github.gfrmoretti.ReflectionUtils.getFieldsJoinSuperClass;
import static io.github.gfrmoretti.mapperconfig.MapperConfigValidator.isMapperValid;

class FieldMapToRetriever implements Retriever<Field> {
    private final Class<?> classToSearchField;
    private final Field annotatedField;
    private final Class<?> sourceClass;
    private final Class<?> targetClass;

    @Nullable
    private Map mapAnnotation;

    public FieldMapToRetriever(Field annotatedField, Class<?> classToSearchField, Class<?> sourceClass, Class<?> targetClass) {
        this.annotatedField = annotatedField;
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.classToSearchField = classToSearchField;
    }

    @Override
    public boolean canRetrieveValue() {
        if (isAnnotationPresent()) {
            if (annotatedField.isAnnotationPresent(Map.class))
                if (isMapperValid(sourceClass, targetClass, annotatedField.getAnnotation(Map.class).mapperConfig())) {
                    mapAnnotation = annotatedField.getAnnotation(Map.class);
                    return true;
                }

            if (annotatedField.isAnnotationPresent(RepeatableMap.class)) {
                return canRetrieveIfRepeatableConfigIsValid();
            }
        }
        return false;
    }

    private boolean isAnnotationPresent() {
        return annotatedField.isAnnotationPresent(Map.class) || annotatedField.isAnnotationPresent(RepeatableMap.class);
    }

    private boolean canRetrieveIfRepeatableConfigIsValid() {
        var canRetrieve = false;
        for (Map map : annotatedField.getAnnotation(RepeatableMap.class).value()) {
            if (isMapperValid(sourceClass, targetClass, map.mapperConfig())) {
                canRetrieve = true;
                mapAnnotation = map;
                break;
            }
        }
        return canRetrieve;
    }

    @Override
    public Optional<Field> retrieve() {
        if (mapAnnotation == null)
            return Optional.empty();
        var declaredFields = getFieldsJoinSuperClass(classToSearchField);
        Field foundField = null;
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(mapAnnotation.value()))
                foundField = declaredField;
        }
        return Optional.ofNullable(foundField);
    }
}
