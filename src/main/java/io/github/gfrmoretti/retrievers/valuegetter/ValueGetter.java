package io.github.gfrmoretti.retrievers.valuegetter;

import io.github.gfrmoretti.annotations.MapperConfig;
import io.github.gfrmoretti.retrievers.Retriever;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.github.gfrmoretti.mapperconfig.MapperConfigValidator.isMapperValid;

public abstract class ValueGetter implements Retriever<Object> {
    protected final Field annotatedField;
    protected final Field sourceField;
    protected final Field targetField;
    protected final Object source;

    public ValueGetter(Object source, Field annotatedField, Field sourceField, Field targetField) {
        this.annotatedField = annotatedField;
        this.source = source;
        this.sourceField = sourceField;
        this.targetField = targetField;
    }

    protected abstract Class<? extends Annotation> annotation();

    protected abstract Class<? extends Annotation> repeatableAnnotation();

    protected abstract MapperConfig getMapperConfigFromAnnotation();

    protected abstract MapperConfig getMapperConfigFromRepeatableAnnotation(int index);

    protected abstract int getLengthRepeatableAnnotation();

    protected abstract void setRepeatableAnnotation(int index);

    protected abstract void setAnnotation();

    @Override
    public boolean canRetrieveValue() {
        if (isAnnotationPresent()) {
            if (annotatedField.isAnnotationPresent(annotation()))
                if (isMapperValid(sourceField.getDeclaringClass(), targetField.getDeclaringClass(), getMapperConfigFromAnnotation())) {
                    setAnnotation();
                    return true;
                }

            if (annotatedField.isAnnotationPresent(repeatableAnnotation())) {
                return canRetrieveIfRepeatableConfigIsValid();
            }
        }
        return false;
    }

    private boolean isAnnotationPresent() {
        return annotatedField.isAnnotationPresent(annotation()) || annotatedField.isAnnotationPresent(repeatableAnnotation());
    }

    private boolean canRetrieveIfRepeatableConfigIsValid() {
        var canRetrieve = false;
        for (int i = 0; i < getLengthRepeatableAnnotation(); i++) {
            if (isMapperValid(sourceField.getDeclaringClass(), targetField.getDeclaringClass(), getMapperConfigFromRepeatableAnnotation(i))) {
                canRetrieve = true;
                setRepeatableAnnotation(i);
                break;
            }
        }
        return canRetrieve;
    }
}
