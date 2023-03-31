package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Class to convert string of transfer objects to integer on domain objects and vice-versa.
 */
@Slf4j
public class IntegerToStringFunctionMapper implements FunctionMapper<Integer, String> {

    @Override
    public Optional<String> mapValue(Integer value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(value.toString());
        } catch (Exception e) {
            log.debug("Problem to convert to integer", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> inverseMapValue(String value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(Integer.parseInt(value));
        } catch (Exception e) {
            log.warn("Problem to convert to integer", e);
            return Optional.empty();
        }
    }
}
