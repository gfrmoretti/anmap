package features.models.users;

import io.github.gfrmoretti.annotations.ImplicitMap;
import io.github.gfrmoretti.annotations.Map;
import features.models.users.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUuid {
    @Map("uuid")
    @ImplicitMap
    private UserId id;
    private String name;
}
