package io.github.gfrmoretti.annotations.repeatables;

import io.github.gfrmoretti.annotations.Map;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatableMap {
    Map[] value();
}
