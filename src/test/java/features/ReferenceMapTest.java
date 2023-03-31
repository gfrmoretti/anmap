package features;

import features.models.Person;
import features.models.PersonDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.github.gfrmoretti.AnMap.mapReference;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReferenceMapTest {

    private boolean personsAreEquals(Person person, PersonDto personDto) {
        return person.getName().equals(personDto.getName()) &&
                person.getAge().equals(personDto.getAge());
    }

    @Test
    @DisplayName("Should set all field on objet when map by reference.")
    void shouldSetAllFieldsMapByReference() {
        var source = new Person("name", 10);
        var reference = new PersonDto("other name", 12);

        mapReference(source, reference);

        assertTrue(personsAreEquals(source, reference));
    }

    @Test
    @DisplayName("Should set non null fields on objet when map by reference.")
    void shouldSetOnlyNotNullFieldsMapByReference() {
        var source = new Person("name", null);
        var reference = new PersonDto("other name", 12);

        mapReference(source, reference);

        assertEquals(reference.getAge(), reference.getAge());
    }

    @Test
    @DisplayName("Should update field when map by reference.")
    void shouldUpdateFieldWhenMapByReference() {
        var source = new Person("name", 20);
        var reference = new Person("other name", 12);

        mapReference(source, reference);

        assertEquals(source, reference);
    }

    @Test
    @DisplayName("Should update field when map by reference or throw.")
    void shouldUpdateFieldWhenMapByReferenceOrThrow() {
        var source = new Person("name", 20);
        var reference = new Person("other name", 12);

        mapReference(source, reference);

        assertEquals(source, reference);
    }

    @Test
    @DisplayName("Should throw exception if target are null.")
    void shouldThrowExceptionIfTargetNull() {
        var source = new Person("name", 20);

        assertThrows(IllegalArgumentException.class, () -> mapReference(source, null));
    }

    @Test
    @DisplayName("Should throw exception if source are null.")
    void shouldThrowExceptionIfSourceNull() {
        var reference = new Person("other name", 12);

        assertThrows(IllegalArgumentException.class, () -> mapReference(null, reference));
    }
}
