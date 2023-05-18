package features.models;

import io.github.gfrmoretti.annotations.ImplicitMap;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class PersonDto {
    private String name;
    private Integer age;
    @ImplicitMap
    private LogInfoDto logInfo;
}
