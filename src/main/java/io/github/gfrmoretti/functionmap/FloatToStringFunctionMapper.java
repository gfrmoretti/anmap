package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Class to float values from source to string value on target class and vice-versa.
 */
@Slf4j
public class FloatToStringFunctionMapper implements FunctionMapper<Float, String> {

    @Override
    public Optional<String> mapValue(Float value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.ofNullable(value.toString());
        } catch (Exception e) {
            log.debug("Problem to convert to float", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Float> inverseMapValue(String value) {
        try {
            if(value == null) return Optional.empty();
            return Optional.of(Float.parseFloat(value));
        } catch (Exception e) {
            log.debug("Problem to convert to float", e);
            return Optional.empty();
        }
    }
}
