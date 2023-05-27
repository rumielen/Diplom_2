package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateUserTest {
    private String accessToken;
    private final UserClient userClient = new UserClient();
    private final UserGenerator userGenerator = new UserGenerator();

    User user1 = userGenerator.getRandomUser();
    private final UserRequest userRequest = new UserRequest();



    @Test
    @DisplayName("Проверяем, что пользователь создается успешно")
    public void checkUserCreateSuccessfully() {
        ValidatableResponse create = userClient.createUser(user1);
        accessToken = userRequest.assertUserCreatedSuccessfully(create);
    }

    @Test
    @DisplayName("Проверяем, что нельзя создать двух одинаковых пользователей")
    public void checkTwoSameUsersCanNotCreated() {
        ValidatableResponse create = userClient.createUser(user1);
        accessToken = userRequest.assertUserCreatedSuccessfully(create);
        ValidatableResponse createTheSameUser = userClient.createUser(user1);
        userRequest.assertCreateUserWhichAlreadyExist(createTheSameUser);
    }

    @Test
    @DisplayName("Проверяем, что пользователь без электронной почты не создается")
    public void checkUserNotCreatedWithNoEmail() {
        user1.setEmail(null);
        ValidatableResponse create = userClient.createUser(user1);
        userRequest.assertUserCreateWithNoField(create);
    }
    @Test
    @DisplayName("Проверяем, что пользователь не создается без пароля")
    public void checkCreateUserNoPassword() {
        user1.setPassword(null);
        ValidatableResponse create = userClient.createUser(user1);
        userRequest.assertUserCreateWithNoField(create);
    }
    @Test
    @DisplayName("Проверяем, чтол пользователь не создается без указания имени")
    public void checkUserNotCreatedWithNoName() {
        user1.setName(null);
        ValidatableResponse create = userClient.createUser(user1);
        userRequest.assertUserCreateWithNoField(create);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.deleteUser(accessToken);
            userRequest.assertUserDeleteSuccessfully(response);
        }
    }
}
