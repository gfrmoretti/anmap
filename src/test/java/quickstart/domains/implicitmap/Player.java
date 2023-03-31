package quickstart.domains.implicitmap;

import io.github.gfrmoretti.annotations.DateMap;
import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class Player {
    private String name;
    @Map("points")
    @ImplicitMap
    private Point point;
    @DateMap(formatPattern = "yyyy-MM-dd HH:mm:ss Z")
    private Instant entranceDate;
}
