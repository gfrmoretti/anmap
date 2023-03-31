package quickstart.requests.bestof;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class HouseRequest {
    private long id;
    private String nameOfOwner;
    private List<DoorRequest> doors;
    private ValuationStatusRequest valuationStatusRequest;
    private String acquisitionDate;
    private double value;
}
