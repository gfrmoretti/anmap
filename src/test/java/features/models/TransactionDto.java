package features.models;

import io.github.gfrmoretti.annotations.ConstructorMap;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.functionmap.BigDecimalToStringFunctionMapper;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@ToString
public class TransactionDto {
    @Map("id")
    private @Nullable String uuid;
    private final @NotNull String name;
    private @Nullable List<String> details;
    @FunctionMap(BigDecimalToStringFunctionMapper.class)
    private final @NotNull String value;
    private final @Nullable String extra;

    @ConstructorMap()
    public TransactionDto(@NotNull String name, @NotNull String value) {
        this.name = name;
        this.value = value;
        this.extra = "Used this constructor";
    }

    @ConstructorMap(priority = 1, acceptNullValues = true)
    public TransactionDto(@Nullable String uuid, @Nullable String name,
                          @Nullable List<String> details, @NotNull String value) {
        this.uuid = uuid;
        this.name = name == null ? "default" : name;
        this.details = details;
        this.value = value;
        this.extra = null;
    }
}
