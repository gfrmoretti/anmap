package quickstart.domains.functionmap;

import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.collectors.CollectorType;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import io.github.gfrmoretti.functionmap.LongToStringFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class Document {
    @FunctionMap(LongToStringFunctionMapper.class)
    private String id;
    @FunctionMap(IntegerToStringFunctionMapper.class)
    private List<String> codes;
    @FunctionMap(value = IntegerToStringFunctionMapper.class, collector = CollectorType.SET)
    private Set<String> codesSet;
    @FunctionMap(EmailToStringFunctionMapper.class)
    private Email email;
}
