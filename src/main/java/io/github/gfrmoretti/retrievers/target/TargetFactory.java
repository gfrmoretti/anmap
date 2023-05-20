package io.github.gfrmoretti.retrievers.target;

import io.github.gfrmoretti.conf.AnnotationSide;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import static io.github.gfrmoretti.ConstructorUtils.fullFillParamValueList;

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
        fullFillParamValueList(source, targetClass, annotationSide, parameterValueList, constructor, false);

        var targetConstructor = targetClass.getDeclaredConstructor(constructor.getParameterTypes());
        targetConstructor.setAccessible(true);
        return targetConstructor.newInstance(parameterValueList.toArray());
    }

    Target createWithAnnotatedConstructor(Constructor<?> constructor,
                                          boolean acceptNullValues) throws Exception {
        var parameterValueList = new ArrayList<>();
        fullFillParamValueList(source, targetClass, annotationSide, parameterValueList, constructor, acceptNullValues);

        var targetConstructor = targetClass.getDeclaredConstructor(constructor.getParameterTypes());
        targetConstructor.setAccessible(true);
        return targetConstructor.newInstance(parameterValueList.toArray());
    }


    Target createWithEmptyConstructor() throws Exception {
        var constructor = targetClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }


}
