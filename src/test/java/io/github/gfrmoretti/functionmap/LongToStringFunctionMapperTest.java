package io.github.gfrmoretti.functionmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LongToStringFunctionMapperTest {

    private final LongToStringFunctionMapper function = new LongToStringFunctionMapper();

    @Test
    public void shouldConvertToString() {
        var result = function.mapValue(262413212123L).orElse(null);
        assertEquals("262413212123", result);
    }


    @Test
    public void shouldConvertToLong() {
        var result = function.inverseMapValue("123456789000").orElse(null);
        assertEquals(123456789000L, result);
    }

    @Test
    public void shouldNotConvertToLong() {
        var result = function.inverseMapValue("25abc").orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToLong() {
        var result = function.inverseMapValue(null).orElse(null);
        assertNull(result);
    }

    @Test
    public void shouldNotConvertNullToString() {
        var result = function.mapValue(null).orElse(null);
        assertNull(result);
    }
}