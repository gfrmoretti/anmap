package io.github.gfrmoretti.functionmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BooleanToStringMappingFunctionTest {

    private final BooleanToStringFunctionMapper function = new BooleanToStringFunctionMapper();

    @Test
    public void shouldConvertTrueToString() {
        var result = function.mapValue(true).orElse(null);
        assertEquals("true", result);
    }

    @Test
    public void shouldConvertFalseToString() {
        var result = function.mapValue(false).orElse(null);
        assertEquals("false", result);
    }

    @Test
    public void shouldConvertToBooleanTrue() {
        var result = function.inverseMapValue("true").orElse(null);
        assertEquals(true, result);
    }

    @Test
    public void shouldConvertToBooleanFalse() {
        var result = function.inverseMapValue("false").orElse(null);
        assertEquals(false, result);
    }

    @Test
    public void shouldNotConvertToBoolean() {
        var result = function.inverseMapValue("2.5").orElse(null);
        assertEquals(false, result);
    }

    @Test
    public void shouldNotConvertNullToBoolean() {
        var result = function.inverseMapValue(null).orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToString() {
        var result = function.mapValue(null).orElse(null);
        assertNull(result);
    }
}
