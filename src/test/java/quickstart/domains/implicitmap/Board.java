package quickstart.domains.implicitmap;

import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.functionmap.LongToStringFunctionMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Board {
    @FunctionMap(LongToStringFunctionMapper.class)
    private long id;
    private String name;
}
