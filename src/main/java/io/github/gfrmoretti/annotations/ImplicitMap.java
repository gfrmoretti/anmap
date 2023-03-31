package io.github.gfrmoretti.annotations;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableImplicitMap;
import io.github.gfrmoretti.collectors.CollectorType;
import io.github.gfrmoretti.conf.AnnotationSide;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to implicit map a field. In other words, will get the field em call the map function in the field.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = RepeatableImplicitMap.class)
public @interface ImplicitMap {
    /**
     * This indicates which collection collector use to convert the collection.
     * If the field that you want to map is a collection, you can choose if map should use List or Set.
     * Use to map a list of object source to a list of object target.
     *
     * @return collector type.
     */
    CollectorType collectorType() default CollectorType.LIST;

    /**
     * This indicates which side of the mapping the implicit map should use to process the annotations.
     *
     * @return annotations side to read.
     */
    AnnotationSide annotationSide() default AnnotationSide.AUTO_DETECT;

    /**
     * The configuration to map the annotation.
     *
     * @return mapper annotation config.
     */
    MapperConfig mapperConfig() default @MapperConfig(sourceClass = AnMap.class, targetClass = AnMap.class);
}
