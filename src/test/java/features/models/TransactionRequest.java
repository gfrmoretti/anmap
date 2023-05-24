package features.models;

import io.github.gfrmoretti.annotations.ConstructorMap;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.functionmap.BigDecimalToStringFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor(onConstructor = @__(@ConstructorMap(fillArgsNotFoundWithNull = true)))
public class TransactionRequest {
    private final @NotNull String name;
    @FunctionMap(BigDecimalToStringFunctionMapper.class)
    private final @NotNull String value;
    private final @Nullable String extra;
    private final @Nullable String fileName;
    private final @Nullable Integer number;
    @Map("id")
    private @Nullable String uuid;
    private @Nullable List<String> details;
}
