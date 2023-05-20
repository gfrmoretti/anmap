package features.models;

import io.github.gfrmoretti.annotations.ConstructorMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor(onConstructor = @__(@ConstructorMap(priority = 0, acceptNullValues = true)))
public class LogInfo {
    private String id;
    private String message;
}
