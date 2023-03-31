package io.github.gfrmoretti.functionmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloatToStringFunctionMapperTest {


    private final FloatToStringFunctionMapper function = new FloatToStringFunctionMapper();

    @Test
    public void shouldConvertToString() {
        var result = function.mapValue(2f).orElse(null);
        assertEquals("2.0", result);
    }


    @Test
    public void shouldConvertToFloat() {
        var result = function.inverseMapValue("2.50").orElse(null);
        assertEquals(2.5f, result);
    }

    @Test
    public void shouldNotConvertToFloat() {
        var result = function.inverseMapValue("2.5abc").orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToFloat() {
        var result = function.inverseMapValue(null).orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToString() {
        var result = function.mapValue(null).orElse(null);
        assertNull(result);
    }
}