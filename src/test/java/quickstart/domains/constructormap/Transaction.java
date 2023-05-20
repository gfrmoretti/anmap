package quickstart.domains.constructormap;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ToString
public class Transaction {
    private final @NotNull String name;
    private final @NotNull BigDecimal value;
    private final @Nullable String id;
    private final @Nullable List<String> details;

    public Transaction(@Nullable String id, @NotNull String name,
                       @Nullable List<String> details, @NotNull BigDecimal value) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.value = value;
    }
}
