package io.github.gfrmoretti.functionmap;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class BigDecimalToStringFunctionMapper implements FunctionMapper<BigDecimal, String> {
    @Override
    public Optional<String> mapValue(BigDecimal value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(value.toPlainString());
        } catch (Exception e) {
            log.debug("Problem to convert to BigDecimal", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> inverseMapValue(String value) {
        try {
            if (value == null) return Optional.empty();
            return Optional.of(new BigDecimal(value));
        } catch (Exception e) {
            log.debug("Problem to convert to BigDecimal", e);
            return Optional.empty();
        }
    }
}
