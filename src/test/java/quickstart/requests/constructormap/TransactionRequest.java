package quickstart.requests.constructormap;

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
public class TransactionRequest {
    @Map("id")
    private @Nullable String uuid;
    private @NotNull String name;
    private @Nullable List<String> details;
    @FunctionMap(BigDecimalToStringFunctionMapper.class)
    private @NotNull String value;
    private @Nullable String priority;

    @ConstructorMap(priority = 0, acceptNullValues = true)
    public TransactionRequest(@Nullable String name, @Nullable String value, @Nullable List<String> details) {
        this.name = name == null ? "default" : name;
        this.value = value == null ? "value_default" : value;
        this.details = details;
        this.priority = "priority 0";
    }

    @ConstructorMap(priority = 1)
    public TransactionRequest(@NotNull String name, @NotNull String value) {
        this.name = name;
        this.value = value;
        this.priority = "priority 1";
    }

    @ConstructorMap(priority = 2, acceptNullValues = true)
    public TransactionRequest(@Nullable String uuid, @Nullable String name,
                              @Nullable List<String> details, @NotNull String value) {
        this.uuid = uuid;
        this.name = name == null ? "default" : name;
        this.details = details;
        this.value = value;
        this.priority = "priority 2";
    }
}
