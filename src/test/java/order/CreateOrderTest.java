package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.*;

import java.util.List;

import static javax.management.Query.and;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static user.RestClient.BASE_URL;

public class CreateOrderTest {
    private String accessToken;
     private final UserGenerator userGenerator = new UserGenerator();
        private final UserRequest userRequest = new UserRequest();
        private final UserClient userClient = new UserClient();

        private final OrderClient orderClient = new OrderClient();
        User user1 = userGenerator.getRandomUser();
        OrderRequest orderRequest = new OrderRequest();


        @Before
        public void userCreate() {
            ValidatableResponse create = userClient.createUser(user1);
            accessToken = userRequest.assertUserCreatedSuccessfully(create);
        }


        @DisplayName("Проверяем, что зарегистрированный пользователь может создать заказ ")
        @Test
        public void checkRegisteredUserCanCreateOrder() {

            List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa6f");
            Order order = new Order(ingredients);
            ValidatableResponse response = orderClient.createOrder(accessToken, order);
            orderRequest.assertOrderCreatedSuccessfully(response);
        }

        @DisplayName("Проверяем возможность создания заказа незарегистрированным пользователем")
        @Test
        public void checkNotRegisteredUserCanNotCreateOrder() {
            List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa6f");
            Order order = new Order(ingredients);
            ValidatableResponse response = orderClient.createOrder("0", order);
            orderRequest.assertOrderCreatedSuccessfully(response);
        }

        @DisplayName("Проверка появления ошибки при заказе без ингридиентов")
        @Test
        public void checkOrderNotCreatedWithNoIngredients() {
            Order order = new Order();
            ValidatableResponse response = orderClient.createOrder(accessToken, order);
            orderRequest.assertOrderCreateWithNoIngredients(response);
        }
        @DisplayName("Проверяем, что при попытке заказа c неверным хешем ингредиентов возникает ошибка")
        @Test
        public void checkOrderNotCreatedWithIncorrectHash() {
            List<String> ingredients = List.of("11111111111f820dff01bdaaa6d", "6166666d1f82dfsdf001bdaaa6f");
            Order order = new Order(ingredients);
            ValidatableResponse response = orderClient.createOrder(accessToken, order);
            orderRequest.assertOrderCreateWithIncorrectHash(response);
        }


        @After
        public void deleteUser() {
            if (accessToken != null) {
                ValidatableResponse response = userClient.deleteUser(accessToken);
                userRequest.assertUserDeleteSuccessfully(response);
            }
        }

}
