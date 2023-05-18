package features.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class LogInfo {
    private String id;
    private String message;
}
