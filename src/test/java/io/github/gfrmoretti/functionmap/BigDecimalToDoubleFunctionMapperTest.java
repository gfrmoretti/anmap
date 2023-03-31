package io.github.gfrmoretti.functionmap;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BigDecimalToDoubleFunctionMapperTest {
    private final BigDecimalToDoubleFunctionMapper function = new BigDecimalToDoubleFunctionMapper();

    @Test
    public void shouldConvertValueToDouble() {
        var result = function.mapValue(new BigDecimal("100.98")).orElse(null);
        assertEquals(100.98, result);
    }

    @Test
    public void shouldConvertToBigDecimal() {
        var result = function.inverseMapValue(85.39).orElse(null);
        assertEquals(new BigDecimal("85.39"), result);
    }

    @Test
    public void shouldNotConvertNullToBigDecimal() {
        var result = function.inverseMapValue(null).orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToString() {
        var result = function.mapValue(null).orElse(null);
        assertNull(result);
    }

}