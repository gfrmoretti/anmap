package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Class to short values from source to string value on target class and vice-versa.
 */
@Slf4j
public class ShortToStringFunctionMapper implements FunctionMapper<Short, String> {

    @Override
    public Optional<String> mapValue(Short value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(String.valueOf(value));
        } catch (Exception e) {
            log.debug("Problem to convert to short", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Short> inverseMapValue(String valueTarget) {
        try {
            return Optional.of(Short.parseShort(valueTarget));
        } catch (Exception e) {
            log.debug("Problem to convert to short", e);
            return Optional.empty();
        }
    }
}
