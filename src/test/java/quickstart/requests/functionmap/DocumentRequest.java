package quickstart.requests.functionmap;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class DocumentRequest {
    private Long id;
    private List<Integer> codes;
    private Set<Integer> codesSet;
    private String email;
}
