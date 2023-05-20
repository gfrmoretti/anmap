package io.github.gfrmoretti;

import io.github.gfrmoretti.conf.AnnotationSide;
import io.github.gfrmoretti.retrievers.target.TargetRetrieverCreator;
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
            var targetRetrieverCreator = new TargetRetrieverCreator<>(source, targetClass, annotationSide);
            var target = targetRetrieverCreator.tryCreateTargetInstance().orElseThrow();

            return map(source, target, annotationSide);
        } catch (Exception e) {
            log.warn("Problem to map source, can not found any valid constructor.");
            return Optional.empty();
        }
    }

    private static <Source, Target> Optional<Target> map(@NotNull Source source,
                                                         @NotNull Target target,
                                                         @NotNull AnnotationSide annotationSide) {
        FieldsMapper.mapFields(source, target.getClass(), null, annotationSide, false,
                (targetField, sourceValue) -> {
                    targetField.setAccessible(true);
                    targetField.set(target, sourceValue);
                }
        );
        return Optional.of(target);
    }

    static <Source, Target> Optional<Target> mapReference(@NotNull Source source,
                                                          @NotNull Target target,
                                                          @NotNull AnnotationSide annotationSide) {
        FieldsMapper.mapFieldsReference(source, target, annotationSide, (targetField, sourceValue) -> {
            targetField.setAccessible(true);
            targetField.set(target, sourceValue);
        });

        return Optional.of(target);
    }
}
