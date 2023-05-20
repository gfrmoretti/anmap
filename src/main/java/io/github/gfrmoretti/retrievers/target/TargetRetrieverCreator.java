package io.github.gfrmoretti.retrievers.target;

import io.github.gfrmoretti.conf.AnnotationSide;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Slf4j
public class TargetRetrieverCreator<Source, Target> {

    private final List<TargetRetriever<Target>> retrievers;

    public TargetRetrieverCreator(@NotNull Source source,
                                  @NotNull Class<Target> targetClass,
                                  @NotNull AnnotationSide annotationSide) {
        var factory = new TargetFactory<>(source, targetClass, annotationSide);
        var constructors = targetClass.getDeclaredConstructors();

        this.retrievers = List.of(
                new TargetAnnotatedConstructorRetriever<>(constructors, factory),
                new TargetRegularConstructorRetriever<>(constructors, factory),
                new TargetEmptyConstructorRetriever<>(constructors, factory)
        );
    }

    public Optional<Target> tryCreateTargetInstance() {

        for (TargetRetriever<Target> retriever : retrievers) {
            if (retriever.canRetrieveValue()) {
                var result = retriever.retrieve();
                if (result.isPresent())
                    return result;
            }
        }

        return Optional.empty();
    }
}
