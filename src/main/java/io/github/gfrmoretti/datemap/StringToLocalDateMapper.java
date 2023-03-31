package io.github.gfrmoretti.datemap;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Slf4j
class StringToLocalDateMapper implements TemporalMapper {
    private final DateTimeFormatter formatter;

    public StringToLocalDateMapper(String formatPattern) {
        this.formatter = DateTimeFormatter.ofPattern(formatPattern).withZone(ZoneOffset.UTC);
    }

    public Optional<TemporalAccessor> mapToTemporal(String s) {
        try {
            if (s == null)
                return Optional.empty();
            return Optional.ofNullable(formatter.parse(s, LocalDate::from));
        } catch (Exception e) {
            log.warn("Problem to convert to local date", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> mapToString(TemporalAccessor temporal) {
        try {
            if (temporal == null)
                return Optional.empty();
            return Optional.of(formatter.format(temporal));
        } catch (Exception e) {
            log.warn("Problem to convert to string date", e);
            return Optional.empty();
        }
    }
}
