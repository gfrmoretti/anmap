package features;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.annotations.MapperConfig;
import io.github.gfrmoretti.datemap.StringToTemporalMapper;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import io.github.gfrmoretti.functionmap.LongToStringFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import static io.github.gfrmoretti.AnMap.mapOrElseThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapperConfigClassesTest {

    private Source source;

    @BeforeEach
    void setup() {
        source = new Source();
        source.setName("gabriel");
        source.setAge(26);
        source.setBornDate("21-04-2012");
        source.setDocument("123456789");
        source.setCivilState(CivilState.SINGLE);
        source.setAddress(new Address("street", "123", "rb"));
    }

    @Test
    void test() throws NoSuchFieldException {
        var result = mapOrElseThrow(source, Target.class);
        var result2 = mapOrElseThrow(source, Target2.class);
        var result3 = mapOrElseThrow(source, Target3.class);

        assertTrue(target1Valid(result));
        assertTrue(target2Valid(result2));
        assertTrue(target3Valid(result3));
    }

    private boolean target3Valid(Target3 target) {
        return Objects.equals(source.getName(), target.getName())
                && Objects.isNull(target.getAge())
                && Objects.equals(source.getBornDate(), target.getBornDate())
                && Objects.equals(source.getDocument(), String.valueOf(target.getDocument()))
                && Objects.equals(source.getCivilState().name(), target.getCivilState().name())
                && Objects.isNull(target.getTargetAddress());
    }

    private boolean target2Valid(Target2 target) {
        return Objects.equals(source.getName(), target.getSimpleName())
                && Objects.isNull(target.getAge())
                && Objects.equals(source.getBornDate(), new StringToTemporalMapper(LocalDate.class, "dd-MM-yyyy").mapToString(target.getBornDate()).orElseThrow())
                && Objects.equals(source.getDocument(), String.valueOf(target.getDocument()))
                && Objects.equals(source.getCivilState().name(), target.getCivilState().name())
                && Objects.isNull(target.getTargetAddress());
    }

    private boolean target1Valid(Target target) {
        return Objects.equals(source.getName(), target.getFullName())
                && Objects.equals(String.valueOf(source.getAge()), target.getAge())
                && Objects.equals(source.getBornDate(), new StringToTemporalMapper(Instant.class, "dd-MM-yyyy").mapToString(target.getBornDate()).orElseThrow())
                && Objects.equals(source.getDocument(), String.valueOf(target.getDocument()))
                && Objects.equals(source.getCivilState().getValue(), target.getCivilState())
                && Objects.equals(source.getAddress(), mapOrElseThrow(target.getTargetAddress(), Address.class));
    }


    @Getter
    @AllArgsConstructor
    public enum CivilState {
        SINGLE("single"),
        MARRIED("married");

        private final String value;
    }

    public enum TargetCivilState {
        SINGLE,
        MARRIED
    }

    @Data
    private static class Source {
        @Map(value = "fullName", mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target.class))
        @Map(value = "simpleName", mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
        private String name;
        @FunctionMap(value = IntegerToStringFunctionMapper.class, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target.class))
        @IgnoreMap(mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
        @IgnoreMap(mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target3.class))
        private Integer age;
        @DateMap(formatPattern = "dd-MM-yyyy", mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target.class))
        @DateMap(formatPattern = "dd-MM-yyyy", mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
        private String bornDate;
        @FunctionMap(value = IntegerToStringFunctionMapper.class, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target.class))
        @FunctionMap(value = LongToStringFunctionMapper.class, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
        @FunctionMap(value = IntegerToStringFunctionMapper.class, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target3.class))
        private String document;
        @EnumMap(enumMapType = EnumMapType.MAP_VALUE, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target.class))
        @EnumMap(enumMapType = EnumMapType.MAP_ENUM, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
        @EnumMap(enumMapType = EnumMapType.MAP_ENUM, mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target3.class))
        private CivilState civilState;
        @Map("targetAddress")
        @ImplicitMap(mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target.class))
        @IgnoreMap(mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target2.class))
        @IgnoreMap(mapperConfig = @MapperConfig(sourceClass = Source.class, targetClass = Target3.class))
        private Address address;
    }

    @Data
    private static class Address {
        private final String street;
        private final String number;
        private final String city;

        public Address(String street, String number, String city) {
            this.street = street;
            this.number = number;
            this.city = city;
        }
    }

    @Data
    private static class Target {
        @Map("test")
        private String fullName;
        private String age;
        private Instant bornDate;
        private Integer document;
        private String civilState;
        private TargetAddress targetAddress;
    }

    @Data
    private static class TargetAddress {
        private final String street;
        private final String number;
        private final String city;

        public TargetAddress(String street, String number, String city) {
            this.street = street;
            this.number = number;
            this.city = city;
        }
    }

    @Data
    private static class Target2 {
        private String simpleName;
        private String age;
        private LocalDate bornDate;
        private Long document;
        private TargetCivilState civilState;
        private TargetAddress targetAddress;
    }

    @Data
    private static class Target3 {
        private String name;
        private String age;
        private String bornDate;
        private Integer document;
        private TargetCivilState civilState;
        private TargetAddress targetAddress;
    }
}
