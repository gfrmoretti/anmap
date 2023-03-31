package features;

import io.github.gfrmoretti.AnMap;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.collectors.CollectorType;
import io.github.gfrmoretti.functionmap.DoubleToStringFunctionMapper;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionMapWithListTest {

    @Test
    @DisplayName("Should convert values in list during the map.")
    void shouldMapValuesConvertingUsingFunctionClass() {
        var source = new Source("Moretti", List.of("21", "26", "19", "65"), Set.of(10.0, 6.7, 9.8, 4.8));

        var result = AnMap.mapOrElseThrow(source, Target.class);

        assertEquals("Moretti", result.name);
        assertArrayEquals(List.of(21, 26, 19, 65).toArray(), result.ages.toArray());
        assertEquals(Set.of("10.0", "6.7", "9.8", "4.8"), result.grades);
    }

    @Data
    @AllArgsConstructor
    private static class Source {
        private String name;
        @FunctionMap(IntegerToStringFunctionMapper.class)
        private List<String> ages;
        @FunctionMap(value = DoubleToStringFunctionMapper.class, collector = CollectorType.SET)
        private Set<Double> grades;
    }

    @Data
    @AllArgsConstructor
    private static class Target {
        private String name;
        private List<Integer> ages;
        private Set<String> grades;
    }
}
