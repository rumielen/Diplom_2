package user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
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
    @Step("Проверяем, что можно успешно залогиниться с корректными данными")
    public void checkUserCanLoginSuccessfully() {
        UserCredentials userCredentials = UserCredentials.from(user1);
        ValidatableResponse login = userClient.loginUser(userCredentials);
        userRequest.assertUserAuthorizationIsSuccess(login);
    }


    @Test
    @DisplayName("Проверяем, что при входе с некорректным паролем появляется ошибка")
    public void checkUserCantLoginWithIncorrectPassword() {
        UserCredentials userCredentials = UserCredentials.from(user1);
        userCredentials.setPassword(userCredentials.getPassword() + "0");
        ValidatableResponse login = userClient.loginUser(userCredentials);
        userRequest.assertUserAuthorizationWithIncorrectFields(login);
    }

    @Test
    @DisplayName("Проверяем, что при входе с некорректным е-майлом появляется сообщение об ошибке")
    public void checkUserDoesntLoginWithIncorrectEmail() {
        UserCredentials userCredentials = UserCredentials.from(user1);
        userCredentials.setEmail(userCredentials.getEmail() + "aaa");
        ValidatableResponse login = userClient.loginUser(userCredentials);
        userRequest.assertUserAuthorizationWithIncorrectFields(login);
    }



    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.deleteUser(accessToken);
            userRequest.assertUserDeleteSuccessfully(response);
        }
    }
}
