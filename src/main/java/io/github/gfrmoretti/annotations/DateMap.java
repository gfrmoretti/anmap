package io.github.gfrmoretti.annotations;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableDateMap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mapping dates to String.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = RepeatableDateMap.class)
public @interface DateMap {
    /**
     * Specify the pattern to format the date to String.
     * <p><b>Example:</b></p>
     * <ul>
     *     <li>yyyy-MM-dd</li>
     *     <li>dd/MM/yyyy hh:mm:ss</li>
     *     <li>yyyy-MM-dd HH:mm:ss Z</li>
     * </ul>
     *
     * @return The pattern to format.
     */
    String formatPattern();

    /**
     * The mapper configuration.
     *
     * @return the config mapper annotation.
     */
    MapperConfig mapperConfig() default @MapperConfig(sourceClass = AnMap.class, targetClass = AnMap.class);
}
