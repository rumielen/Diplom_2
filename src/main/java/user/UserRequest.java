package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;


public class UserRequest {
    @Step("Успешный запрос на создание пользователя")
        public String assertUserCreatedSuccessfully(ValidatableResponse response) {
            return response.assertThat()
                    .statusCode(200)
                    .body("success", is(true))
                    .extract().path("accessToken");
        }

        @Step("Успешное удаление пользователя")
        public void assertUserDeleteSuccessfully(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(202)
                    .body("message", equalTo("User successfully removed"));
        }

    @Step("Создание пользователя c пустым обязательным полем, ответ с ошибкой")
    public void assertUserCreateWithNoField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

        @Step("Получение ошибки при создании пользователя с уже существующим емайлом")
        public void assertCreateUserWhichAlreadyExist(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(403)
                    .body("message", equalTo("User already exists"));
        }

        @Step("Ответ с ошбикой при неверном логине или пароле")
        public void assertUserAuthorizationWithIncorrectFields(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(401)
                    .body("message", equalTo("email or password are incorrect"));
        }

        @Step("Ответ при успешной авторизации")
        public void assertUserAuthorizationIsSuccess(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(200)
                    .body("accessToken", notNullValue());
        }


        @Step("Успешное изменение данных зарегистрированным пользователем")
        public void assertUserDataChangeSuccessfully(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(200)
                    .body("success", is(true));
        }
        @Step("Ответ при изменении данных незарегестрированным пользователем")
        public void assertNotRegisteredUserCanNotChangeData(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(401)
                    .body("message", equalTo("You should be authorised"));
        }
}
