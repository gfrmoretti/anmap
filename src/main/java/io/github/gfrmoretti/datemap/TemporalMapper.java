package io.github.gfrmoretti.datemap;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public interface TemporalMapper {
    Optional<TemporalAccessor> mapToTemporal(String value);

    Optional<String> mapToString(TemporalAccessor temporal);
}
