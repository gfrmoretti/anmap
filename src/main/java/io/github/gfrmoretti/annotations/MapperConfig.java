package io.github.gfrmoretti.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure when the annotation should be considered based in the source and target class.
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperConfig {
    /**
     * Which source class should be considered to map.
     *
     * @return source class.
     */
    Class<?> sourceClass();

    /**
     * Which target class should be considered to map.
     *
     * @return target class.
     */
    Class<?> targetClass();
}
