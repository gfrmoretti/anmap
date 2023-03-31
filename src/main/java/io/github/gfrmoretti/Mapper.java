package io.github.gfrmoretti;

import io.github.gfrmoretti.conf.AnnotationSide;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Slf4j
class Mapper {

    static <Source, Target> Optional<Target> map(@Nullable Source source,
                                                 @Nullable Class<Target> targetClass,
                                                 @NotNull AnnotationSide annotationSide) {
        if (source == null || targetClass == null) return Optional.empty();
        try {
            var target = tryCreateTargetInstance(source, targetClass, annotationSide).orElseThrow();

            return map(source, target, annotationSide);
        } catch (Exception e) {
            log.warn("Problem to map source, can not found any valid constructor.");
            return Optional.empty();
        }
    }

    private static <Source, Target> Optional<Target> tryCreateTargetInstance(@NotNull Source source,
                                                                             @NotNull Class<Target> targetClass,
                                                                             @NotNull AnnotationSide annotationSide) {
        try {
            Target result = null;

            var factory = new TargetFactory<>(source, targetClass, annotationSide);
            for (var constructor : targetClass.getDeclaredConstructors()) {
                if (TargetFactory.constructorHasArguments(constructor)) {
                    try {
                        result = factory.createWithConstructor(constructor);
                        break;
                    } catch (Exception e) {
                        log.debug("Cannot construct", e);
                    }
                }
            }

            if (result == null)
                return Optional.of(factory.createWithEmptyConstructor());

            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    static <Source, Target> Optional<Target> map(@NotNull Source source,
                                                 @NotNull Target target,
                                                 @NotNull AnnotationSide annotationSide) {
        FieldsMapper.mapFields(source, target.getClass(), annotationSide, (targetField, sourceValue) -> {
            targetField.setAccessible(true);
            targetField.set(target, sourceValue);
        });

        return Optional.of(target);
    }
}
