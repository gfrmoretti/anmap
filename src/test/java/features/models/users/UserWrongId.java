package features.models.users;

import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import features.models.Transfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWrongId {
    @Map("uuid")
    @ImplicitMap
    private Transfer id;
    private String name;
}
