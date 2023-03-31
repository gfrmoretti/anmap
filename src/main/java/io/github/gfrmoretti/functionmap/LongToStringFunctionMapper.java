package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Class to long values from source to string value on target class and vice-versa.
 */
@Slf4j
public class LongToStringFunctionMapper implements FunctionMapper<Long, String> {
    @Override
    public Optional<String> mapValue(Long value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.ofNullable(String.valueOf(value));
        } catch (Exception e) {
            log.debug("Problem to convert to long", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> inverseMapValue(String value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(Long.parseLong(value));
        } catch (Exception e) {
            log.debug("Problem to convert to long", e);
            return Optional.empty();
        }
    }
}
