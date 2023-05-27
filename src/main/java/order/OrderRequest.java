package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.RestClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderRequest extends RestClient {

        final static String ORDER_PATH = "/api/orders";


        @Step("Ответ при успешном создании заказа")
        public void assertOrderCreatedSuccessfully(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(200)
                    .body("success", is(true));
        }

        @Step("Ответ при получении списка заказа зарегистрированным пользователем")
        public void assertRegisteredUserGetOrder(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(200)
                    .body("success", is(true));
        }
        @Step("Ответ при попытке получения заказа незарегистрированным пользователем")
        public void assertNotRegisteredUserGetOrder(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(401)
                    .body("message", equalTo("You should be authorised"));
        }

        @Step("Создание заказа без ингридиентов")
        public void assertOrderCreateWithNoIngredients(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(400)
                    .body("message", equalTo("Ingredient ids must be provided"));
        }
        @Step("Ответ при запросе создания заказа с неверным хэшем")
        public void assertOrderCreateWithIncorrectHash(ValidatableResponse response) {
            response.assertThat()
                    .statusCode(500);
        }



    }

