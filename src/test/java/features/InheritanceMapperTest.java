package features;

import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.github.gfrmoretti.AnMap.mapOrElseThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InheritanceMapperTest {
    @Test
    @DisplayName("Should map class with inheritance.")
    void shouldMapClassWithInheritance() {
        var source = new Child("grand", "father", "name", 12);
        var result = mapOrElseThrow(source, ChildTarget.class);

        assertEquals("grand", result.getGrandFatherName());
        assertEquals("father", result.getFatherName());
        assertEquals("name", result.getFullName());
        assertEquals("12", result.getAgeStr());
        assertEquals(source.getCreated(), result.getCreatedAt());
    }

    @Test
    @DisplayName("Should map class with inheritance with target without inheritance.")
    void shouldMapClassWithInheritanceTargetWithoutInheritance() {
        var source = new Child("grand", "father", "name", 50);
        var result = mapOrElseThrow(source, SingleTarget.class);

        assertEquals("grand", result.getGrandFatherName());
        assertEquals("father", result.getFatherName());
        assertEquals("name", result.getFullName());
        assertEquals("50", result.getAgeStr());
        assertEquals(source.getCreated(), result.getCreatedAt());
    }

    @Data
    private static abstract class GrandFather {
        protected String grandFatherName;
        @Map("createdAt")
        protected Instant created = Instant.now();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class Father extends GrandFather {
        protected String fatherName;
        @Map("ageStr")
        @FunctionMap(IntegerToStringFunctionMapper.class)
        protected Integer age;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class Child extends Father {
        @Map("fullName")
        private final String name;

        Child(String grand, String father, String name, Integer age) {
            this.grandFatherName = grand;
            this.fatherName = father;
            this.name = name;
            this.age = age;
        }
    }

    @Data
    private static abstract class GrandFatherTarget {
        protected String grandFatherName;
        protected Instant createdAt;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class FatherTarget extends GrandFatherTarget {
        protected String fatherName;
        protected String ageStr;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class ChildTarget extends FatherTarget {
        private String fullName;

        ChildTarget(String grandFatherName, String fatherName, String fullName) {
            this.grandFatherName = grandFatherName;
            this.fatherName = fatherName;
            this.fullName = fullName;
        }
    }

    @Data
    private static class SingleTarget {
        private String grandFatherName;
        private String fatherName;
        private String fullName;
        private String ageStr;
        private Instant createdAt;
    }

}
