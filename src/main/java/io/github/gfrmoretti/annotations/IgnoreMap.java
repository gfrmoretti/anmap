package io.github.gfrmoretti.annotations;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableIgnoreMap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to ignore a field and do not map it.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = RepeatableIgnoreMap.class)
public @interface IgnoreMap {
    /**
     * The configuration to map the annotation.
     *
     * @return mapper annotation config.
     */
    MapperConfig mapperConfig() default @MapperConfig(sourceClass = AnMap.class, targetClass = AnMap.class);
}
