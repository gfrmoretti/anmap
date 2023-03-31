package io.github.gfrmoretti;

import io.github.gfrmoretti.conf.AnnotationSide;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Objects;

import static io.github.gfrmoretti.ReflectionUtils.getParameterNames;

class TargetFactory<Source, Target> {

    private final Source source;
    private final Class<Target> targetClass;
    private final AnnotationSide annotationSide;

    TargetFactory(Source source, Class<Target> targetClass, AnnotationSide annotationSide) {
        this.source = source;
        this.targetClass = targetClass;
        this.annotationSide = annotationSide;
    }

    static boolean constructorHasArguments(Constructor<?> constructor) {
        return constructor.getParameters().length > 0;
    }

    Target createWithConstructor(Constructor<?> constructor) throws Exception {
        var parameterValueList = new ArrayList<>();
        fullFillParamValueList(parameterValueList, constructor);

        var targetConstructor = targetClass.getDeclaredConstructor(constructor.getParameterTypes());
        targetConstructor.setAccessible(true);
        return targetConstructor.newInstance(parameterValueList.toArray());
    }

    Target createWithEmptyConstructor() throws Exception {
        var constructor = targetClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private void fullFillParamValueList(final ArrayList<Object> parameterValueList,
                                        Constructor<?> constructor) throws Exception {
        for (var paramName : getParameterNames(constructor)) {
            FieldsMapper.mapFields(source, targetClass, annotationSide, (targetField, sourceValue) -> {
                if (Objects.equals(targetField.getName(), paramName))
                    parameterValueList.add(sourceValue);
            });
        }
    }

}
