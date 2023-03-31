package features.models;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.IgnoreMap;
import io.github.gfrmoretti.annotations.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferLocalTime {
    @Map("id")
    public String uuid;
    public double number;
    @IgnoreMap
    public int size;
    @DateMap(formatPattern = "dd/MM/yyyy HH:mm:ss")
    public LocalDateTime date;
    public String name;
}
