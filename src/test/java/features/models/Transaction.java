package features.models;

import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.functionmap.BigDecimalToStringFunctionMapper;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ToString
public class Transaction {
    @Map("uuid")
    private @Nullable String id;
    private final @Nullable String name;
    private @Nullable List<String> details;
    @FunctionMap(BigDecimalToStringFunctionMapper.class)
    private final @NotNull BigDecimal value;

    public Transaction(@Nullable String name, @NotNull BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public Transaction(@Nullable String id, @NotNull String name,
                       @Nullable List<String> details, @NotNull BigDecimal value) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.value = value;
    }
}
