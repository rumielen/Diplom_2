package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.RestClient;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    //POST https://stellarburgers.nomoreparties.site/api/
    private final static String ORDER = "/api/orders";

    @Step("Запрос на создание заказа")
    public ValidatableResponse createOrder(String token, Order order) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }
    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList(String token) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .when()
                .get(ORDER)
                .then();
    }
}
