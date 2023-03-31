package quickstart.domains.enummap;

import io.github.gfrmoretti.annotations.EnumMap;
import io.github.gfrmoretti.enummap.EnumMapType;
import io.github.gfrmoretti.functionmap.IntegerToStringFunctionMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
    private String id;
    @EnumMap
    private PaymentCoin coin;
    @EnumMap(enumMapType = EnumMapType.MAP_VALUE)
    private StatusAccounting statusAccounting;
    @EnumMap(enumMapType = EnumMapType.MAP_VALUE,
            functionName = "getType",
            functionMapper = IntegerToStringFunctionMapper.class)
    private PaymentType paymentType;
    @EnumMap(enumMapType = EnumMapType.MAP_ENUM)
    private StatusTransfer statusTransfer;
    @EnumMap(enumMapType = EnumMapType.MAP_ENUM, matchEnumMapByEnumValue = true,
            functionMapper = IntegerToStringFunctionMapper.class)
    private PaymentValueType paymentValueType;
    @EnumMap(enumMapType = EnumMapType.MAP_ORDINAL, mustMapOrdinalToInt = true)
    private ReturnStatus returnStatus;
}
