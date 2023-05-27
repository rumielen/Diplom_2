package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UpdateUserDataTest {
    private String accessToken;
    private final UserRequest userRequest = new UserRequest();
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();

    User user1 = userGenerator.getRandomUser();


    @Before
    public void userCreate() {
        ValidatableResponse create = userClient.createUser(user1);
        accessToken = userRequest.assertUserCreatedSuccessfully(create);
    }

    @Test
    @DisplayName("Проверяем возможность изменения пароля зарегистрированным пользователем")
    public void checkUserCanUpdatePassword() {
        user1.setPassword(RandomStringUtils.randomAlphabetic(8));
        ValidatableResponse response = userClient.update(accessToken, user1);
        userRequest.assertUserDataChangeSuccessfully(response);
    }

    @Test
    @DisplayName("Проверяем, что пользователь может изменить е-майл")
    public void checkUserCanUpdateEmail() {
        user1.setEmail(RandomStringUtils.randomAlphabetic(6)+ "@mail.ru");
        ValidatableResponse response = userClient.update(accessToken, user1);
        userRequest.assertUserDataChangeSuccessfully(response);
    }


    @Test
    @DisplayName("Проверяем возможность изменить имя пользователя")
    public void checkUserCanUpdateName() {
        user1.setName("Василий");
        ValidatableResponse response = userClient.update(accessToken, user1);
        userRequest.assertUserDataChangeSuccessfully(response);
    }

    @Test
    @DisplayName("Проверяем запрет смены пароля незарегистрированным пользователем")
    public void checkNotAuthorizedUserCanNotUpdatePassword() {
        user1.setPassword(RandomStringUtils.randomAlphabetic(8));
        ValidatableResponse response = userClient.update("0", user1);
        userRequest.assertNotRegisteredUserCanNotChangeData(response);
    }

    @Test
    @DisplayName("Проверяем запрет смены е-майла незарегистрированным пользователем")
    public void checkNotAuthorizedUserCanNotUpdateEmail() {
        user1.setEmail(RandomStringUtils.randomAlphabetic(6)+ "@mail.ru");
        ValidatableResponse response = userClient.update("0", user1);
        userRequest.assertNotRegisteredUserCanNotChangeData(response);
    }

    @Test
    @DisplayName("Проверяем запрет смены имени незарегистрированным пользователем")
    public void checkNotAuthorizedUserCanNotUpdateName() {
        user1.setName("Пётр");
        ValidatableResponse response = userClient.update("0", user1);
        userRequest.assertNotRegisteredUserCanNotChangeData(response);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.deleteUser(accessToken);
            userRequest.assertUserDeleteSuccessfully(response);
        }
    }


}
