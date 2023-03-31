package io.github.gfrmoretti.annotations;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.repeatables.RepeatableEnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.functionmap.DefaultFunctionMapper;
import io.github.gfrmoretti.functionmap.FunctionMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mapping enums.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = RepeatableEnumMap.class)
public @interface EnumMap {

    /**
     * Type the enum mapping type relationship.
     * <ul>
     *     <li>MAP_STRING <i style='font-size: 10px'>(enum to string and vice-versa)</i></li>
     *     <li>MAP_ORDINAL <i style='font-size: 10px'>(enum to ordinal integer or string and vice-versa)</i></li>
     *     <li>MAP_VALUE <i style='font-size: 10px'>(enum to value in enum and vice-versa)</i></li>
     *     <li>MAP_ENUM <i style='font-size: 10px'>(enum to enum and vice-versa)</i></li>
     * </ul>
     *
     * @return an {@link EnumMapType}.
     */
    EnumMapType enumMapType() default EnumMapType.MAP_STRING;

    /**
     * When {@link EnumMapType} is <b>TO_ORDINAL</b> this indicates if mapper should convert ordinal value to string or integer.
     *
     * @return if mapper should convert to integer.
     */
    boolean mustMapOrdinalToInt() default false;

    /**
     * When {@link EnumMapType} is <b>TO_ENUM</b> the mapper will search in the target enum by value, trying to get the
     * value from the target enum. To configure this logic will you can use the <b>functionName</b> to inform the name of the
     * method to get the value from enum and if you need conversion you can use the <b>functionMapper</b> config to inform
     * which function use to convert the value before do the match.
     *
     * @return if mapper should match source enum with target enum by the value inside them.
     */
    boolean matchEnumMapByEnumValue() default false;

    /**
     * When {@link EnumMapType} is <b>TO_VALUE</b> this indicates what function the mapper must use to convert the enum
     * value inside the enum. If none function was passed none conversion is made.
     *
     * @return The function to convert.
     */
    Class<? extends FunctionMapper<?, ?>> functionMapper() default DefaultFunctionMapper.class;

    /**
     * When {@link EnumMapType} is <b>TO_VALUE</b> this indicates what the name of function to get the value inside the
     * enum.
     * <p>
     * For example, if you have a field named value inside the enum and a getter called getValue, then the mapper
     * will use these name to get the value from enum, if you have another functions names you must personalize here.
     * </p>
     *
     * @return function name to get the value from enum.
     */
    String functionName() default "getValue";

    /**
     * The configuration to map the annotation.
     *
     * @return mapper annotation config.
     */
    MapperConfig mapperConfig() default @MapperConfig(sourceClass = AnMap.class, targetClass = AnMap.class);

}
