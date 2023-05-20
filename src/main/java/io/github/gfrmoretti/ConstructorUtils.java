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
}
