package features.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LogInfoDto {
    private String id;
    private String message;

    public LogInfoDto(String message) {
        this.message = message;
    }
}
