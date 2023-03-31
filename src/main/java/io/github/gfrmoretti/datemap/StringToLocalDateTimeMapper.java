package io.github.gfrmoretti.datemap;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Slf4j
class StringToLocalDateTimeMapper implements TemporalMapper {
    private final DateTimeFormatter formatter;
    private final String formatPattern;

    public StringToLocalDateTimeMapper(String formatPattern) {
        this.formatter = DateTimeFormatter.ofPattern(formatPattern).withZone(ZoneOffset.UTC);
        this.formatPattern = formatPattern;
    }

    public Optional<TemporalAccessor> mapToTemporal(String s) {
        try {
            if (s == null)
                return Optional.empty();
            if (formatPattern.contains("HH") || formatPattern.contains("hh"))
                return Optional.of(formatter.parse(s, LocalDateTime::from));
            if (formatPattern.contains("dd") || formatPattern.contains("D"))
                return Optional.of(formatter.parse(s, LocalDate::from)
                        .atTime(0, 0));
        } catch (Exception e) {
            log.warn("Problem to convert to local date time", e);
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
            log.warn("Problem to convert to string date time", e);
            return Optional.empty();
        }
    }
}
