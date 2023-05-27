package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserGenerator;
import user.UserRequest;

public class OrderListTest {
    private final UserRequest userRequest = new UserRequest();
    private final UserGenerator userGenerator = new UserGenerator();
    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();


    User user1 = userGenerator.getRandomUser();
    OrderRequest orderRequest = new OrderRequest();
    private String accessToken;


    @Before
    public void userCreate() {
        ValidatableResponse create = userClient.createUser(user1);
        accessToken = userRequest.assertUserCreatedSuccessfully(create);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.deleteUser(accessToken);
            userRequest.assertUserDeleteSuccessfully(response);
        }
    }

    @DisplayName("Проверка возможности получить список заказов зарегистрированному пользователю")
    @Test
    public void checkGetOrderListAuthorizedUser() {
        ValidatableResponse response = orderClient.getOrdersList(accessToken);
        orderRequest.assertRegisteredUserGetOrder(response);
    }
    @DisplayName("Провека невозможности получения списка заказов незарегистрированным пользователем")
    @Test
    public void checkGetOrderListNotAuthorizedUser() {
        ValidatableResponse response = orderClient.getOrdersList("");
        orderRequest.assertNotRegisteredUserGetOrder(response);
    }
}
