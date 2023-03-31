package quickstart.domains.functionmap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    private String value;

    public String getFormattedEmail() {
        return value.trim();
    }
}
