package io.github.gfrmoretti.functionmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IntegerToStringMappingFunctionTest {

    private final IntegerToStringFunctionMapper function = new IntegerToStringFunctionMapper();

    @Test
    public void shouldConvertToString() {
        var result = function.mapValue(2).orElse(null);
        assertEquals("2", result);
    }

    @Test
    public void shouldConvertToInt() {
        var result = function.inverseMapValue("3").orElse(null);
        assertEquals(3, result);
    }

    @Test
    public void shouldNotConvertToInt() {
        var result = function.inverseMapValue("2.5abc").orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToInt() {
        var result = function.inverseMapValue(null).orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToString() {
        var result = function.mapValue(null).orElse(null);
        assertNull(result);
    }

}
