package quickstart.requests.implicitmap;

import lombok.Data;

@Data
public class PlayerRequest {
    private String name;
    private PointRequest points;
    private String entranceDate;
}
