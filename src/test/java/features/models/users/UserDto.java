package features.models.users;

import io.github.gfrmoretti.annotations.ImplicitMap;
import features.models.users.ids.UserIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @ImplicitMap
    private UserIdDto id;
    private String name;
    @ImplicitMap
    private List<UserIdDto> ids;
}
