package quickstart.requests.bestof;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DoorRequest {
    private int height;
    private int width;
}
