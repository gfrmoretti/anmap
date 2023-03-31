package io.github.gfrmoretti.datemap;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@AllArgsConstructor
public enum DateMappingType {
    INSTANT(StringToInstantMapper.class),
    LOCAL_DATE(StringToLocalDateMapper.class),
    LOCAL_DATE_TIME(StringToLocalDateTimeMapper.class),
    ZONED_DATE_TIME(StringToZonedDateTimeMapper.class);
    private final Class<? extends TemporalMapper> temporalMapper;

    public static DateMappingType findDateMappingTypeByInstance(Class<?> temporalClass) {
        if (temporalClass.equals(Instant.class))
            return INSTANT;
        if (temporalClass.equals(LocalDate.class))
            return LOCAL_DATE;
        if (temporalClass.equals(LocalDateTime.class))
            return LOCAL_DATE_TIME;
        if (temporalClass.equals(ZonedDateTime.class))
            return ZONED_DATE_TIME;
        throw new RuntimeException("Not found temporal date type.");
    }

    public TemporalMapper createTemporalMapper(String pattern) throws Exception {
        return temporalMapper.getDeclaredConstructor(String.class).newInstance(pattern);
    }
}
