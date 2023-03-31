package features;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.datemap.StringToTemporalMapper;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static io.github.gfrmoretti.AnMap.map;
import static io.github.gfrmoretti.AnMap.mapList;
import static io.github.gfrmoretti.AnMap.mapOrElseThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTest {

    private static boolean isEqualObjTargetToObjSource(ObjTarget target, ObjSource source) {
        return target.getAge().equals(Integer.parseInt(source.getAge()))
                && target.getEmail().equals(source.getEmail())
                && target.getFullName().equals(source.getName())
                && Arrays.equals(target.getValues().toArray(),
                mapList(source.getValues(), TargetImplicit.class).toArray())
                && target.getCreateAt().equals(
                new StringToTemporalMapper(Instant.class, "dd/MM/yyyy").mapToString(source.getCreateAt())
                        .orElseThrow());
    }

    @Test
    @DisplayName("Should map an object with constructor.")
    void shouldMapObjectWithConstructor() {
        var source = ObjSource.builder()
                .name("name test")
                .createAt(Instant.now())
                .age("1")
                .email("email")
                .values(List.of(new ImplicitTest("any value")))
                .build();

        var result = mapOrElseThrow(source, ObjTarget.class);
        System.out.println(result);

        assertTrue(isEqualObjTargetToObjSource(result, source));
    }

    @Test
    @DisplayName("Should use second constructor if fails the first.")
    void shouldUseSecondConstructorIfFirstFails() {
        var simpleSource = SimpleObjSource.builder()
                .age("12")
                .name("name")
                .values(List.of(new ImplicitTest("ANY")))
                .build();

        var target = mapOrElseThrow(simpleSource, ObjTarget.class);

        assertTrue(
                target.getAge().equals(Integer.parseInt(simpleSource.getAge()))
                        && target.getFullName().equals(simpleSource.getName())
                        && Arrays.equals(target.getValues().toArray(),
                        mapList(simpleSource.getValues(), TargetImplicit.class).toArray())
                        && target.getCreateAt().equals("12/12/2022")
                        && target.getEmail() == null
        );
    }

    @Test
    @DisplayName("Should use empty constructor if none is found.")
    void shouldUseEmptyConstructorIfNoneIsFound() {
        var simpleSource = SimpleObjSource.builder()
                .name("name")
                .build();

        var target = mapOrElseThrow(simpleSource, ObjTarget.class);

        assertEquals(target.getFullName(), simpleSource.getName());
        assertNull(target.getAge());
        assertNull(target.getCreateAt());
        assertNull(target.getValues());
        assertNull(target.getEmail());
    }

    @Test
    @DisplayName("Should not found any constructor.")
    void shouldNotFoundContructor() {
        var simpleSource = SimpleObjSource.builder()
                .name("name")
                .build();

        var target = map(simpleSource, WrongTarget.class);

        assertTrue(target.isEmpty());
    }


    @Data
    private static class WrongTarget {
        private final String fullName;

        private WrongTarget(String fullName, Integer wrong) {
            this.fullName = fullName;
        }
    }

    @Data
    private static class ObjTarget {
        private final String createAt;
        private final String fullName;
        private final Integer age;
        private final List<TargetImplicit> values;
        private String email;

        public ObjTarget(String fullName, String createAt, Integer age, List<TargetImplicit> values) {
            this.fullName = fullName;
            this.createAt = createAt;
            this.age = age;
            this.values = values;
        }

        public ObjTarget(Integer age, List<TargetImplicit> values) {
            this.fullName = "test";
            this.createAt = "12/12/2022";
            this.age = age;
            this.values = values;
        }

        public ObjTarget() {
            this.values = null;
            this.age = null;
            this.fullName = null;
            this.createAt = null;
        }
    }

    @Data
    private static class TargetImplicit {
        private String value;
    }

    @Builder
    @Data
    private static class ObjSource {
        @Map("fullName")
        private String name;
        @FunctionMap(IntegerToStringFunctionMapper.class)
        private String age;
        @DateMap(formatPattern = "dd/MM/yyyy")
        private Instant createAt;
        @ImplicitMap
        private List<ImplicitTest> values;
        private String email;
    }

    @Builder
    @Data
    private static class SimpleObjSource {
        @Map("fullName")
        private String name;
        @FunctionMap(IntegerToStringFunctionMapper.class)
        private String age;
        @ImplicitMap
        private List<ImplicitTest> values;
    }


    @Data
    private static class ImplicitTest {
        private final String value;
    }
}
