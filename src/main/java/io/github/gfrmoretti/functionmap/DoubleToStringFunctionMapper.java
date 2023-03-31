package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Class to double values from source to string value on target class and vice-versa.
 */
@Slf4j
public class DoubleToStringFunctionMapper implements FunctionMapper<Double, String> {

    @Override
    public Optional<String> mapValue(Double value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.ofNullable(value.toString());
        } catch (Exception e) {
            log.warn("Problem to convert to double", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Double> inverseMapValue(String value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(Double.parseDouble(value));
        } catch (Exception e) {
            log.warn("Problem to convert to double", e);
            return Optional.empty();
        }
    }
}
