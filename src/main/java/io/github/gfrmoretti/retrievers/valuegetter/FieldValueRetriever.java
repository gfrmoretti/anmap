package io.github.gfrmoretti.retrievers.valuegetter;

import io.github.gfrmoretti.retrievers.Retriever;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Optional;

@Slf4j
class FieldValueRetriever implements Retriever<Object> {

    private final Field sourceField;
    private final Object source;


    public FieldValueRetriever(Object source, Field sourceField) {
        this.source = source;
        this.sourceField = sourceField;
    }

    @Override
    public boolean canRetrieveValue() {
        return true;
    }

    @Override
    public Optional<Object> retrieve() {
        try {
            sourceField.setAccessible(true);
            if (sourceField.get(source) == null)
                return Optional.empty();
            if (sourceField.get(source).getClass().isEnum())
                return Optional.ofNullable(sourceField.get(source).toString());
            return Optional.ofNullable(sourceField.get(source));

        } catch (IllegalAccessException e) {
            log.warn("Problem to retrieve value from field");
        }
        return Optional.empty();
    }
}
