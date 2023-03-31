package io.github.gfrmoretti.retrievers.targetfield;


import io.github.gfrmoretti.retrievers.Retriever;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static io.github.gfrmoretti.ReflectionUtils.getFieldsJoinSuperClass;

class FieldNameRetriever implements Retriever<Field> {
    private final Field annotatedField;
    private final Class<?> classToSearchField;

    public FieldNameRetriever(Field annotatedField, Class<?> classToSearchField) {
        this.annotatedField = annotatedField;
        this.classToSearchField = classToSearchField;
    }

    @Override
    public boolean canRetrieveValue() {
        var declaredFields = getFieldsJoinSuperClass(classToSearchField);
        return Arrays.stream(declaredFields)
                .anyMatch(declaredField -> declaredField.getName().equals(annotatedField.getName()));
    }

    @Override
    public Optional<Field> retrieve() {
        var fieldName = annotatedField.getName();
        var declaredFields = getFieldsJoinSuperClass(classToSearchField);
        return Arrays.stream(declaredFields)
                .filter(declaredField -> declaredField.getName().equals(fieldName)).findFirst();
    }
}
