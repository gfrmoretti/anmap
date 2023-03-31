package quickstart.domains.datemap;

import io.github.gfrmoretti.annotations.DateMap;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class Person {
    private String name;
    @DateMap(formatPattern = "yyyy-MM-dd")
    private Instant bornAt;
    @DateMap(formatPattern = "dd/MM/yyyy")
    private String motherBirthday;
    private Instant registerAt;
}
