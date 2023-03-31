package features;

import features.models.Transfer;
import features.models.users.User;
import features.models.users.UserDto;
import features.models.users.UserDtoUuid;
import features.models.users.UserUuid;
import features.models.users.UserWrongId;
import features.models.users.ids.UserId;
import features.models.users.ids.UserIdDto;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.gfrmoretti.AnMap.map;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImplicitMapTest {

    private User user;
    private UserDto userDto;
    private UserUuid userUuid;
    private UserDtoUuid userDtoUuid;

    private static boolean dtoUserIsEqualToUser(@Nullable UserDto dto, User user) {
        if (dto == null) return false;
        var dtoInList = dto.getIds().get(0);
        var userInList = user.getIds().get(0);
        return dto.getId().getIdDoc().equals(user.getId().getIdDoc()) &&
                dto.getId().getEmail().equals(user.getId().getEmail()) &&
                dtoIdEqualsUserId(dtoInList, userInList) &&
                dto.getName().equals(user.getName());
    }

    private static boolean dtoUserUuidIsEqualToUserUuid(@Nullable UserDtoUuid dto, UserUuid user) {
        if (dto == null) return false;
        return dto.getUuid().getIdDoc().equals(user.getId().getIdDoc()) &&
                dto.getUuid().getEmail().equals(user.getId().getEmail()) &&
                dto.getName().equals(user.getName());
    }

    private static boolean dtoIdEqualsUserId(@Nullable UserIdDto dto, UserId user) {
        if (dto == null) return false;
        return dto.getIdDoc().equals(user.getIdDoc()) &&
                dto.getEmail().equals(user.getEmail());
    }

    @BeforeEach
    public void setup() {
        var listUserId = List.of(new UserId("1", "mail@mail.com"));
        var listUserDtoId = List.of(new UserIdDto("2", "dtomail@mail.com"));
        user = new User(new UserId("1", "mail@mail.com"), "randomName", listUserId);
        userDto = new UserDto(new UserIdDto("2", "dtomail@mail.com"), "randomNameDto", listUserDtoId);
        userUuid = new UserUuid(new UserId("3", "uuid.mail@mail.com"), "randomNameUUID");
        userDtoUuid = new UserDtoUuid(new UserIdDto("4", "dtoUUIDmail@mail.com"), "randomNameUUIDDto");
    }

    @Test
    @DisplayName("Should map user to dto with implicit map annotation.")
    public void shouldMapImplicitIdUserToUserDto() {
        var dto = map(user, UserDto.class).orElse(null);
        assertTrue(dtoUserIsEqualToUser(dto, user));
    }

    @Test
    @DisplayName("Should map dto to user with implicit map annotation.")
    public void shouldMapImplicitIdUserDtoToUser() {
        var user = map(userDto, User.class).orElse(null);
        assertTrue(dtoUserIsEqualToUser(userDto, user));
    }

    @Test
    @DisplayName("Should map user with name param on implicit map annotation.")
    public void shouldImplicitMapUserWithUuidToDto() {
        var dto = map(userUuid, UserDtoUuid.class).orElse(null);
        assertTrue(dtoUserUuidIsEqualToUserUuid(dto, userUuid));
    }

    @Test
    @DisplayName("Should map dto with name param on implicit map annotation.")
    public void shouldImplicitMapDtoToUserWithUuid() {
        var userUuid = map(userDtoUuid, UserUuid.class).orElse(null);
        assertTrue(dtoUserUuidIsEqualToUserUuid(userDtoUuid, userUuid));
    }

    @Test
    @DisplayName("Should not map if object id not correspond to the class.")
    public void shouldNotMapIdIfNoClassCorrespond() {
        var result = map(userUuid, UserWrongId.class)
                .orElse(new UserWrongId(new Transfer(), "any"));
        assertNull(result.getId());
    }
}
