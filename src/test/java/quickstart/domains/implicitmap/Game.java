package quickstart.domains.implicitmap;

import io.github.gfrmoretti.annotations.ImplicitMap;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Game {
    @ImplicitMap
    private Board board;
    @ImplicitMap
    private List<Player> players;
}
