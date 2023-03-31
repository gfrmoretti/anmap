package quickstart.domains.bestof;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.annotations.FunctionMap;
import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.functionmap.BigDecimalToDoubleFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Data
public class House {
    private long id;
    @Map("nameOfOwner")
    private String ownerName;
    @ImplicitMap
    private List<Door> doors;
    @Map("valuationStatusRequest")
    @EnumMap(enumMapType = EnumMapType.MAP_ENUM)
    private ValuationStatus valuationStatus;
    @DateMap(formatPattern = "yyyy-MM-dd")
    private Instant acquisitionDate;
    @FunctionMap(BigDecimalToDoubleFunctionMapper.class)
    private BigDecimal value;
}
