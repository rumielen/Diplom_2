package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
//POST https://stellarburgers.nomoreparties.site/api/auth/login — эндпоинт для авторизации
// POST https://stellarburgers.nomoreparties.site/api/auth/register - для регистрации


public class UserClient extends RestClient {

        //отдельный класс с запросами к ендпоинтам

        private final static String USER_REG_PATH = "/api/auth/register";
        private final static String USER_LOGIN = "/api/auth/login";
        private final static String USER = "/api/auth/user";

        @Step("Запрос на регистрация пользователя")

    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_REG_PATH)
                .then();
    }

    @Step("Запрос на авторизацию")
    public ValidatableResponse loginUser(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(credentials)
                .post(USER_LOGIN)
                .then();
    }


//DELETE https://stellarburgers.nomoreparties.site/api/auth/user
        @Step("Запрос на удаление пользователя")
        public ValidatableResponse deleteUser(String token) {
             return given()
                    .spec(getBaseSpec())
                    .header("Authorization", token)
                    .when()
                    .delete(USER)
                    .then();
        }


        @Step("Запрос на изменение данных")
        public ValidatableResponse update(String token, User user) {
            return given()
                    .spec(getBaseSpec())
                    .header("Authorization", token)
                    .body(user)
                    .when()
                    .patch(USER)
                    .then();
        }






        public ValidatableResponse delete(int id) {
            return null;
        }
}


