package io.github.gfrmoretti;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.annotations.repeatables.RepeatableDateMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableEnumMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableFunctionMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableIgnoreMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableImplicitMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableMap;
import io.github.gfrmoretti.conf.AnnotationSide;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

import static io.github.gfrmoretti.ReflectionUtils.getFieldsJoinSuperClass;

class AnnotationsSearcher {
    static <Source, Target> AnnotationSide searchAnnotation(@NotNull Source source, @NotNull Class<Target> targetClass) {
        for (Field sourceField : getFieldsJoinSuperClass(source.getClass()))
            if (fieldContainsAnyMapAnnotations(sourceField))
                return AnnotationSide.SOURCE;
        for (Field targetField : getFieldsJoinSuperClass(targetClass))
            if (fieldContainsAnyMapAnnotations(targetField))
                return AnnotationSide.TARGET;
        return AnnotationSide.SOURCE;
    }

    private static boolean fieldContainsAnyMapAnnotations(Field field) {
        return field.isAnnotationPresent(IgnoreMap.class) ||
                field.isAnnotationPresent(Map.class) ||
                field.isAnnotationPresent(FunctionMap.class) ||
                field.isAnnotationPresent(EnumMap.class) ||
                field.isAnnotationPresent(ImplicitMap.class) ||
                field.isAnnotationPresent(DateMap.class) ||
                fieldContainsAnyRepeatableMapAnnotation(field);
    }

    private static boolean fieldContainsAnyRepeatableMapAnnotation(Field field) {
        return field.isAnnotationPresent(RepeatableIgnoreMap.class) ||
                field.isAnnotationPresent(RepeatableMap.class) ||
                field.isAnnotationPresent(RepeatableFunctionMap.class) ||
                field.isAnnotationPresent(RepeatableEnumMap.class) ||
                field.isAnnotationPresent(RepeatableImplicitMap.class) ||
                field.isAnnotationPresent(RepeatableDateMap.class);
    }
}
