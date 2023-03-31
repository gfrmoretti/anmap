package io.github.gfrmoretti.functionmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DoubleToStringMappingFunctionTest {

    private final DoubleToStringFunctionMapper function = new DoubleToStringFunctionMapper();

    @Test
    public void shouldConvertToString() {
        var result = function.mapValue(2d).orElse(null);
        assertEquals("2.0", result);
    }


    @Test
    public void shouldConvertToDouble() {
        var result = function.inverseMapValue("2.50").orElse(null);
        assertEquals(2.5, result);
    }

    @Test
    public void shouldNotConvertToDouble() {
        var result = function.inverseMapValue("2.5abc").orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToDouble() {
        var result = function.inverseMapValue(null).orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToString() {
        var result = function.mapValue(null).orElse(null);
        assertNull(result);
    }

}
