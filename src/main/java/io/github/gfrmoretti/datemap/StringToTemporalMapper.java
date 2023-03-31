package io.github.gfrmoretti.datemap;

import lombok.extern.slf4j.Slf4j;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Slf4j
public class StringToTemporalMapper {

    private final Class<?> temporalClass;
    private final String formatPattern;

    public StringToTemporalMapper(Class<?> temporalClass, String formatPattern) {
        this.temporalClass = temporalClass;
        this.formatPattern = formatPattern;
    }

    public Optional<TemporalAccessor> mapToTemporal(String value) {
        try {
            var type = DateMappingType.findDateMappingTypeByInstance(temporalClass);
            return type.createTemporalMapper(formatPattern).mapToTemporal(value);
        } catch (Exception e) {
            log.warn("Problem to convert to temporal.", e);
            return Optional.empty();
        }
    }

    public Optional<String> mapToString(TemporalAccessor temporalAccessor) {
        try {
            var type = DateMappingType.findDateMappingTypeByInstance(temporalClass);
            return type.createTemporalMapper(formatPattern).mapToString(temporalAccessor);
        } catch (Exception e) {
            log.warn("Problem to convert temporal to string", e);
            return Optional.empty();
        }
    }
}
