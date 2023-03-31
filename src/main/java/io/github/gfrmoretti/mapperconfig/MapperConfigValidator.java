package io.github.gfrmoretti.mapperconfig;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.MapperConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MapperConfigValidator {

    public static <Source, Target> boolean isMapperValid(Class<Source> sourceClass, Class<Target> targetClass, @Nullable MapperConfig mapperConfig) {
        if (mapperConfig == null)
            return false;

        var validSource = mapperConfig.sourceClass();
        var validTarget = mapperConfig.targetClass();

        if (Objects.equals(validSource, AnMap.class) && Objects.equals(validTarget, AnMap.class))
            return true;

        return Objects.equals(sourceClass, validSource) && Objects.equals(targetClass, validTarget);
    }
}
