package quickstart.domains.simplemap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
public class User {
    private @NotNull String login;
    private @NotNull String password;
    private @NotNull Integer remainingTrialDays;
    private @NotNull Status status;
    private @NotNull Address address;
}
