package io.github.gfrmoretti.enummap;

import io.github.gfrmoretti.annotations.EnumMap;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumMapType {
    MAP_STRING(StringEnumMapper.class),
    MAP_ORDINAL(OrdinalEnumMapper.class),
    MAP_VALUE(ValueEnumMapper.class),
    MAP_ENUM(EnumObjectMapper.class);

    private final Class<? extends EnumMapper> enumMappingClass;

    public EnumMapper getEnumMapping(EnumMap annotation, Object sourceValue, Class<?> sourceClass, Class<?> targetClass)
            throws Exception {
        return enumMappingClass.getDeclaredConstructor(EnumMap.class, Object.class, Class.class, Class.class)
                .newInstance(annotation, sourceValue, sourceClass, targetClass);
    }
}

