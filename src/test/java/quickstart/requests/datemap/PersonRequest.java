package quickstart.requests.datemap;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PersonRequest {
    private String name;
    private String bornAt;
    private LocalDate motherBirthday;
    private Instant registerAt;
}