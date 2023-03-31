package quickstart.domains.simplemap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
@Data
public class Address {
    private @NotNull String street;
    private @NotNull Integer number;
    private @NotNull String city;
}
