package io.github.gfrmoretti.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define configuration and order of creation of target objects during the map.
 */
@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstructorMap {

    /**
     * The priority defines the attempt order of creation for a target class.
     *
     * <p>The order is made by numeric order, so <b>ZERO</b> is the first to create. The biggest number is the last</p>
     *
     * @return the numer of priority.
     */
    int priority() default 0;

    /**
     * This property make the AnMap accept map null values to match and pass the null values in the constructor.
     *
     * <p>By default, AnMap ignore null values to make the constructor arguments match, but in order to match
     * correctly, this property overwrite the validation and accept null values to make the match of arguments in
     * the constructor and create the object passing the source null values to the constructor.</p>
     *
     * @return If accepts or not null values in the annotated constructor
     */
    boolean acceptNullValues() default false;

    /**
     * This property make the AnMap fulfill args not found in source with null values to try creates the constructor.
     * <p>
     * Set this property to true automatic makes the constructor accept null values even if the property
     * <i>acceptNullValues</i> is not set or set with false.
     *
     * @return if constructor must be created filling the not found args with null values.
     */
    boolean fillArgsNotFoundWithNull() default false;
}
