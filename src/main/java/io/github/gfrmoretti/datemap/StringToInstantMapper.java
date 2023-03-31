package io.github.gfrmoretti.datemap;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Slf4j
class StringToInstantMapper implements TemporalMapper {
    private final DateTimeFormatter formatter;
    private final String formatPattern;

    public StringToInstantMapper(String formatPattern) {
        this.formatPattern = formatPattern;
        this.formatter = DateTimeFormatter.ofPattern(formatPattern).withZone(ZoneOffset.UTC);
    }

    public Optional<TemporalAccessor> mapToTemporal(String value) {
        try {
            if (value == null)
                return Optional.empty();
            if (formatPattern.contains("Z") || formatPattern.contains("z"))
                return Optional.ofNullable(formatter.parse(value, ZonedDateTime::from).toInstant());
            if (formatPattern.contains("HH") || formatPattern.contains("hh"))
                return Optional.ofNullable(formatter.parse(value, LocalDateTime::from)
                        .atZone(ZoneOffset.UTC)
                        .toInstant());
            if (formatPattern.contains("dd") || formatPattern.contains("D"))
                return Optional.ofNullable(formatter.parse(value, LocalDate::from)
                        .atTime(0, 0)
                        .atZone(ZoneOffset.UTC)
                        .toInstant());
        } catch (Exception e) {
            log.warn("Problem to convert to instant", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> mapToString(TemporalAccessor temporal) {
        try {
            if (temporal == null)
                return Optional.empty();
            return Optional.of(formatter.format(temporal));
        } catch (Exception e) {
            log.warn("Problem to convert to string", e);
            return Optional.empty();
        }
    }
}
