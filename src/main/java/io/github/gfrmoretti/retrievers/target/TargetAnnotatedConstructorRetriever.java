package io.github.gfrmoretti.retrievers.target;

import io.github.gfrmoretti.annotations.ConstructorMap;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
class TargetAnnotatedConstructorRetriever<Target> extends TargetRetriever<Target> {

    public TargetAnnotatedConstructorRetriever(Constructor<?>[] constructors, TargetFactory<?, Target> factory) {
        super(constructors, factory);
    }

    @Override
    public boolean canRetrieveValue() {
        return Arrays.stream(constructors).anyMatch(constructor ->
                constructor.isAnnotationPresent(ConstructorMap.class));
    }

    @Override
    public Optional<Target> retrieve() {
        var annotatedConstructor = Arrays.stream(constructors)
                .filter(this::isAnnotated)
                .sorted(this::compare)
                .collect(Collectors.toList());

        for (Constructor<?> constructor : annotatedConstructor) {
            var annotation = constructor.getAnnotation(ConstructorMap.class);
            try {
                Target result = annotation.fillArgsNotFoundWithNull() ?
                        factory.createFillingWithNull(constructor) :
                        factory.createWithAnnotatedConstructor(constructor, annotation.acceptNullValues());

                return Optional.ofNullable(result);
            } catch (Exception e) {
                log.trace("Cannot annotated construct", e);
            }
        }
        return Optional.empty();
    }

    private boolean isAnnotated(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(ConstructorMap.class);
    }

    private int compare(Constructor<?> o1, Constructor<?> o2) {
        var a1 = o1.getAnnotation(ConstructorMap.class);
        var a2 = o2.getAnnotation(ConstructorMap.class);

        return Integer.compare(a1.priority(), a2.priority());
    }
}
