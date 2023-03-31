package io.github.gfrmoretti.annotations;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableMap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to inform the name of the source/target field to match with the field annotated.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = RepeatableMap.class)
public @interface Map {
    /**
     * This indicates what is the name of the field to search in the source/target class to match and map.
     *
     * @return Name of the field to match.
     */
    String value();

    /**
     * The configuration to map the annotation.
     *
     * @return mapper annotation config.
     */
    MapperConfig mapperConfig() default @MapperConfig(sourceClass = AnMap.class, targetClass = AnMap.class);
}
