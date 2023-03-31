package io.github.gfrmoretti.retrievers.targetfield;


import io.github.gfrmoretti.retrievers.Retriever;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class FieldRetrieverOrchestrator {

    private final List<Retriever<Field>> retrievers;
    private final Field annotatedField;

    private final boolean isSourceAnnotated;

    public FieldRetrieverOrchestrator(Field annotatedField, Class<?> sourceClass,
                                      Class<?> targetClass, boolean isSourceAnnotated) {
        var classToSearchField = isSourceAnnotated ? targetClass : sourceClass;
        this.retrievers = List.of(
                new FieldMapToRetriever(annotatedField, classToSearchField, sourceClass, targetClass),
                new FieldNameRetriever(annotatedField, classToSearchField)
        );
        this.annotatedField = annotatedField;
        this.isSourceAnnotated = isSourceAnnotated;
    }

    public Optional<Field> getSourceField() {
        if (isSourceAnnotated)
            return getFieldAnnotated();

        return getFieldFromRetriever();
    }

    public Optional<Field> getTargetField() {
        if (isSourceAnnotated)
            return getFieldFromRetriever();

        return getFieldAnnotated();
    }

    private Optional<Field> getFieldFromRetriever() {
        for (Retriever<Field> retriever : retrievers) {
            if (retriever.canRetrieveValue()) {
                return retriever.retrieve();
            }
        }
        return Optional.empty();
    }

    private Optional<Field> getFieldAnnotated() {
        for (Retriever<Field> retriever : retrievers) {
            if (retriever.canRetrieveValue()) {
                if (retriever.retrieve().isPresent())
                    return Optional.ofNullable(annotatedField);
            }
        }
        return Optional.empty();
    }
}
