package io.github.gfrmoretti.annotations;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableFunctionMap;
import io.github.gfrmoretti.collectors.CollectorType;
import io.github.gfrmoretti.functionmap.FunctionMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mapping a field value using a function convert the value to another.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = RepeatableFunctionMap.class)
public @interface FunctionMap {
    /**
     * This indicates which {@link FunctionMapper} should be used to map a value to another.
     *
     * @return the function mapper to convert values.
     */
    Class<? extends FunctionMapper<?, ?>> value();

    /**
     * If the field is a collection you can specify if the collection is a List or a Set.
     * Currently, only these two collections are supported.
     * <p>Use case example:</p>
     * <p>You need convert a list of integers to a list of strings during the mapper.</p>
     *
     * @return Collector type.
     */
    CollectorType collector() default CollectorType.LIST;

    /**
     * The configuration to map the annotation.
     *
     * @return mapper annotation config.
     */
    MapperConfig mapperConfig() default @MapperConfig(sourceClass = AnMap.class, targetClass = AnMap.class);
}
