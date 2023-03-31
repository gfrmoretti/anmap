package quickstart.requests.simplemap;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import quickstart.domains.simplemap.Address;

@Data
public class InsertUserRequest {
    private final @NotNull String login;
    private final @NotNull String password;
    private final @NotNull Integer remainingTrialDays;
    private final @NotNull String status;
    private final @NotNull Address address;
}
