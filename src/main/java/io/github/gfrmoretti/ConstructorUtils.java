package io.github.gfrmoretti;

import io.github.gfrmoretti.conf.AnnotationSide;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Objects;

import static io.github.gfrmoretti.ReflectionUtils.getParameterNames;

public class ConstructorUtils {

    public static <Source, Target> void fullFillParamValueList(Source source,
                                                               Class<Target> targetClass,
                                                               AnnotationSide annotationSide,
                                                               ArrayList<Object> parameterValueList,
                                                               Constructor<?> constructor,
                                                               boolean acceptNullValues) throws Exception {

        for (var paramName : getParameterNames(constructor)) {
            FieldsMapper.mapFields(source, targetClass, null, annotationSide, acceptNullValues,
                    (targetField, sourceValue) -> {
                        if (Objects.equals(targetField.getName(), paramName))
                            parameterValueList.add(sourceValue);
                    }
            );
        }
    }

    public static <Source, Target> void fullFillParamValueListFillingWithNull(Source source,
                                                                              Class<Target> targetClass,
                                                                              AnnotationSide annotationSide,
                                                                              ArrayList<Object> parameterValueList,
                                                                              Constructor<?> constructor) throws Exception {

        var params = getParameterNames(constructor);
        for (int i = 0; i < params.size(); i++) {
            var paramName = params.get(i);

            FieldsMapper.mapFields(source, targetClass, null, annotationSide, true,
                    (targetField, sourceValue) -> {
                        if (Objects.equals(targetField.getName(), paramName))
                            parameterValueList.add(sourceValue);
                    }
            );

            if (parameterValueList.size() != i + 1)
                parameterValueList.add(null);
        }
    }
}
