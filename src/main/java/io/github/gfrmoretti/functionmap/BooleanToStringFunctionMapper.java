package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Class to convert boolean values from source to string value on target class and vice-versa.
 */
@Slf4j
public class BooleanToStringFunctionMapper implements FunctionMapper<Boolean, String> {

    @Override
    public Optional<String> mapValue(Boolean value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(value.toString());
        } catch (Exception e) {
            log.warn("Problem to convert to boolean", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> inverseMapValue(String value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(Boolean.parseBoolean(value));
        } catch (Exception e) {
            log.warn("Problem to convert to boolean", e);
            return Optional.empty();
        }
    }
}
