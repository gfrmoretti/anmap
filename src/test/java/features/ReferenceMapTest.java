package features;

import features.models.LogInfo;
import features.models.LogInfoDto;
import features.models.Person;
import features.models.PersonDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static io.github.gfrmoretti.AnMap.mapReference;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReferenceMapTest {

    private boolean personsAreEquals(Person person, PersonDto personDto) {
        return person.getName().equals(personDto.getName()) &&
                person.getAge().equals(personDto.getAge()) &&
                Objects.equals(person.getLogInfo().getId(), personDto.getLogInfo().getId()) &&
                Objects.equals(person.getLogInfo().getMessage(), personDto.getLogInfo().getMessage());
    }

    @Test
    @DisplayName("Should set all field on objet when map by reference.")
    void shouldSetAllFieldsMapByReference() {
        var source = new Person("name", 10, new LogInfo(null, "message update"));
        var reference = new PersonDto("other name", 12, new LogInfoDto("message"));

        mapReference(source, reference);

        assertTrue(personsAreEquals(source, reference));
    }

    @Test
    @DisplayName("Should mqp reference implicit.")
    void shouldMapImplicitByReference() {
        var source = new Person("name", 10, new LogInfo(null, "message update"));
        var reference = new PersonDto("other name", 12, new LogInfoDto("message"));
        reference.getLogInfo().setId("123");

        mapReference(source, reference);

        assertEquals("123", reference.getLogInfo().getId());
    }

    @Test
    @DisplayName("Should set non null fields on objet when map by reference.")
    void shouldSetOnlyNotNullFieldsMapByReference() {
        var source = new Person("name", null, new LogInfo("123", "message update"));
        var reference = new PersonDto("other name", 12, new LogInfoDto("message"));

        mapReference(source, reference);

        assertNotNull(reference.getAge());
    }

    @Test
    @DisplayName("Should update field when map by reference.")
    void shouldUpdateFieldWhenMapByReference() {
        var source = new Person("name", 20, new LogInfo("123", "message update"));
        var reference = new Person("other name", 12, new LogInfo(null, "message"));

        mapReference(source, reference);

        assertEquals(source, reference);
    }

    @Test
    @DisplayName("Should update field when map by reference or throw.")
    void shouldUpdateFieldWhenMapByReferenceOrThrow() {
        var source = new Person("name", 20, new LogInfo("123", "message update"));
        var reference = new Person("other name", 12, new LogInfo(null, "message"));

        mapReference(source, reference);

        assertEquals(source, reference);
    }

    @Test
    @DisplayName("Should throw exception if target are null.")
    void shouldThrowExceptionIfTargetNull() {
        var source = new Person("name", 20, new LogInfo("123", "message update"));

        assertThrows(IllegalArgumentException.class, () -> mapReference(source, null));
    }

    @Test
    @DisplayName("Should throw exception if source are null.")
    void shouldThrowExceptionIfSourceNull() {
        var reference = new Person("other name", 12, new LogInfo("123", "message update"));

        assertThrows(IllegalArgumentException.class, () -> mapReference(null, reference));
    }
}
