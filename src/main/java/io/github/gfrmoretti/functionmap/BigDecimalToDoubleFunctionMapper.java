package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class BigDecimalToDoubleFunctionMapper implements FunctionMapper<BigDecimal, Double> {
    @Override
    public Optional<Double> mapValue(BigDecimal value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(value.doubleValue());
        } catch (Exception e) {
            log.debug("Problem to convert to BigDecimal", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> inverseMapValue(Double value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(new BigDecimal(String.valueOf(value)));
        } catch (Exception e) {
            log.debug("Problem to convert to BigDecimal", e);
            return Optional.empty();
        }
    }
}
