package features.models.users;

import features.models.users.ids.UserIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoUuid {
    private UserIdDto uuid;
    private String name;
}
