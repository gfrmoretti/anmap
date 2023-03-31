package quickstart.requests.implicitmap;

import lombok.Data;

import java.util.List;

@Data
public class GameRequest {
    private BoardRequest board;
    private List<PlayerRequest> players;
}
