package features.models.users;

import features.models.users.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UserId id;
    private String name;
    private List<UserId> ids;
}
