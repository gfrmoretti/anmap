package io.github.gfrmoretti.functionmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShortToStringFunctionMapperTest {


    private final ShortToStringFunctionMapper function = new ShortToStringFunctionMapper();

    @Test
    public void shouldConvertToString() {
        var result = function.mapValue((short) 2).orElse(null);
        assertEquals("2", result);
    }


    @Test
    public void shouldConvertToShort() {
        var result = function.inverseMapValue("2").orElse(null);
        assertEquals((short) 2, result);
    }

    @Test
    public void shouldNotConvertToShort() {
        var result = function.inverseMapValue("2.5abc").orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToShort() {
        var result = function.inverseMapValue(null).orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToString() {
        var result = function.mapValue(null).orElse(null);
        assertNull(result);
    }

}