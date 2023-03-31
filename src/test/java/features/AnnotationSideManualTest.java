package features;

import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.conf.AnnotationSide;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.gfrmoretti.AnMap.mapOrElseThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnnotationSideManualTest {

    @Test
    @DisplayName("Should map considering the source side annotations.")
    void shouldMapSourceSideAnnotations() {
        var source = new Source();
        source.setName("my name");
        source.setAge(15);
        source.setAgeText("21");
        source.setAddress(new Address("street", "numberText", 12, "city"));
        source.setDocumentList(List.of(new Document("123"), new Document("456")));

        var target = mapOrElseThrow(source, Target.class);

        var targetManual = mapOrElseThrow(source, Target.class, AnnotationSide.SOURCE);

        assertEquals(target, targetManual);
        assertEquals(source.getName(), targetManual.getFullName());
        assertEquals(source.getAge(), targetManual.getAge());
        assertEquals(source.getAddress().getStreet(), targetManual.getAddress().getStreet());
        assertEquals(source.getAddress().getNumberText(), targetManual.getAddress().getNumberText());
        assertEquals(source.getAddress().getCity(), targetManual.getAddress().getCity());
        assertEquals(source.getDocumentList().get(0).getValue(), targetManual.getDocumentList().get(0).getValue());
        assertEquals(source.getDocumentList().get(1).getValue(), targetManual.getDocumentList().get(1).getValue());
    }

    @Test
    @DisplayName("Should map considering the target side annotations.")
    void shouldMapTargetSideAnnotations() {
        var source = new Source();
        source.setName("my name");
        source.setAge(15);
        source.setAgeText("21");
        source.setAddress(new Address("street", "numberText", 12, "city"));
        source.setDocumentList(List.of(new Document("123"), new Document("456")));

        var target = mapOrElseThrow(source, Target.class);

        var targetManual = mapOrElseThrow(source, Target.class, AnnotationSide.TARGET);

        assertNotEquals(target, targetManual);
        assertNull(targetManual.getFullName());
        assertEquals(source.getAgeText(), targetManual.getAge().toString());
        assertEquals(source.getAddress().getStreet(), targetManual.getAddress().getStreet());
        assertNotEquals(source.getAddress().getNumberText(), targetManual.getAddress().getNumberText());
        assertEquals(source.getAddress().getNumber().toString(), targetManual.getAddress().getNumberText());
        assertNull(targetManual.getAddress().getCity());
        assertNull(targetManual.getDocumentList().get(0).getValue());
        assertNull(targetManual.getDocumentList().get(1).getValue());
        assertEquals(source.getDocumentList().get(0).getValue(), targetManual.getDocumentList().get(0).getValueTarget());
        assertEquals(source.getDocumentList().get(1).getValue(), targetManual.getDocumentList().get(1).getValueTarget());
    }

    @Data
    private static class Source {
        @Map("fullName")
        private String name;
        private Integer age;
        @IgnoreMap
        private String ageText;
        @ImplicitMap
        private Address address;
        @ImplicitMap(annotationSide = AnnotationSide.SOURCE)
        private List<Document> documentList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Address {
        private String street;
        private String numberText;
        @IgnoreMap
        private Integer number;
        private String city;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Document {
        private String value;
    }

    @Data
    private static class Target {
        @IgnoreMap
        private String fullName;
        @Map("ageText")
        @FunctionMap(IntegerToStringFunctionMapper.class)
        private Integer age;
        @ImplicitMap(annotationSide = AnnotationSide.TARGET)
        private AddressTarget address;
        @ImplicitMap(annotationSide = AnnotationSide.TARGET)
        private List<DocumentTarget> documentList;
    }

    @Data
    private static class AddressTarget {
        private String street;
        @Map("number")
        @FunctionMap(IntegerToStringFunctionMapper.class)
        private String numberText;
        @IgnoreMap
        private String city;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DocumentTarget {
        @Map("value")
        private String valueTarget;
        @IgnoreMap
        private String value;
    }
}
