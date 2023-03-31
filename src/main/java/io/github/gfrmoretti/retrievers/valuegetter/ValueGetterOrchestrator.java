package io.github.gfrmoretti.retrievers.valuegetter;


import io.github.gfrmoretti.retrievers.Retriever;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class ValueGetterOrchestrator {

    List<Retriever<Object>> retrievers;

    public ValueGetterOrchestrator(Object source, Field annotatedField, Field sourceField, Field targetField) {
        this.retrievers = List.of(
                new DateValueRetriever(source, annotatedField, sourceField, targetField),
                new FunctionValueRetriever(source, annotatedField, sourceField, targetField),
                new EnumValueRetriever(source, annotatedField, sourceField, targetField),
                new ImplicitMapValueRetriever(source, annotatedField, sourceField, targetField),
                new FieldValueRetriever(source, sourceField)
        );
    }

    public Optional<Object> getValueToSet() {
        for (Retriever<Object> retriever : retrievers) {
            if (retriever.canRetrieveValue())
                return retriever.retrieve();
        }
        return Optional.empty();
    }
}
