package io.github.gfrmoretti;

import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableIgnoreMap;
import io.github.gfrmoretti.conf.AnnotationSide;
import io.github.gfrmoretti.exceptions.SourceValueNullException;
import io.github.gfrmoretti.mapperconfig.MapperConfigValidator;
import io.github.gfrmoretti.retrievers.targetfield.FieldRetrieverOrchestrator;
import io.github.gfrmoretti.retrievers.valuegetter.ValueGetterOrchestrator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

import static io.github.gfrmoretti.ReflectionUtils.getFieldsJoinSuperClass;

@Slf4j
class FieldsMapper {

    private static <Source, Target> Field[] getAnnotatedFields(@NotNull Source source,
                                                               @NotNull Class<Target> targetClass,
                                                               @NotNull AnnotationSide side) {
        return side.equals(AnnotationSide.SOURCE) ?
                getFieldsJoinSuperClass(source.getClass()) :
                getFieldsJoinSuperClass(targetClass);
    }

    static <Source, Target> void mapFields(@NotNull Source source,
                                           @NotNull Class<? extends Target> targetClass,
                                           @Nullable("Used for reference map.") Target target,
                                           @NotNull AnnotationSide annotationSide,
                                           @NotNull Boolean acceptNullValues,
                                           @NotNull TargetValueCallback callback) {
        var side = annotationSide;
        if (annotationSide.equals(AnnotationSide.AUTO_DETECT))
            side = AnnotationsSearcher.searchAnnotation(source, targetClass);

        var annotationFields = getAnnotatedFields(source, targetClass, side);

        for (Field annotatedField : annotationFields) {
            try {
                if (isIgnoreMapAnnotationPresent(annotatedField)
                        && isIgnoreMapConfigValid(source.getClass(), targetClass, annotatedField))
                    continue;
                var fieldOrchestrator = new FieldRetrieverOrchestrator(annotatedField, source.getClass(),
                        targetClass, side.equals(AnnotationSide.SOURCE));
                var sourceField = fieldOrchestrator.getSourceField().orElseThrow();
                var targetField = fieldOrchestrator.getTargetField().orElseThrow();

                if (sourceValueIsNull(source, sourceField)) {
                    if (acceptNullValues) {
                        callback.executeAction(targetField, sourceField.get(source));
                        continue;
                    }
                    throw new SourceValueNullException();
                }
                var sourceValue = new ValueGetterOrchestrator(source, annotatedField, sourceField, targetField, target)
                        .getValueToSet().orElseThrow();

                callback.executeAction(targetField, sourceValue);
            } catch (SourceValueNullException e) {
                log.trace("Source value is null, can not map '" + annotatedField.getName() + "' because his value is null.");
            } catch (Exception e) {
                log.trace("It can not map the field = '" + annotatedField.getName() + "'");
            }

        }
    }

    static <Source, Target> void mapFieldsReference(@NotNull Source source,
                                                    @NotNull Target target,
                                                    @NotNull AnnotationSide annotationSide,
                                                    @NotNull TargetValueCallback callback) {
        mapFields(source, target.getClass(), target, annotationSide, false, callback);
    }

    private static boolean isIgnoreMapAnnotationPresent(Field annotatedField) {
        return annotatedField.isAnnotationPresent(IgnoreMap.class) || annotatedField.isAnnotationPresent(RepeatableIgnoreMap.class);
    }

    private static boolean isIgnoreMapConfigValid(Class<?> sourceClass, Class<?> targetClass, Field annotatedField) {
        if (annotatedField.isAnnotationPresent(IgnoreMap.class))
            return MapperConfigValidator.isMapperValid(sourceClass, targetClass, annotatedField.getAnnotation(IgnoreMap.class).mapperConfig());
        if (annotatedField.isAnnotationPresent(RepeatableIgnoreMap.class)) {
            for (IgnoreMap ignoreMap : annotatedField.getAnnotation(RepeatableIgnoreMap.class).value()) {
                if (MapperConfigValidator.isMapperValid(sourceClass, targetClass, ignoreMap.mapperConfig()))
                    return true;
            }
        }
        return false;
    }

    private static boolean sourceValueIsNull(Object source, Field sourceField) {
        try {
            sourceField.setAccessible(true);
            return sourceField.get(source) == null;
        } catch (IllegalAccessException e) {
            return true;
        }
    }

}
