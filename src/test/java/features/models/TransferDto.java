package features.models;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    @Map("uuid")
    public String id;
    public Double number;
    @IgnoreMap
    public Integer size;
    @DateMap(formatPattern = "yyyy-MM-dd")
    public String date;
}
