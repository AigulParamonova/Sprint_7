import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import serializer.Order;
import steps.BaseUrls;
import steps.OrderSteps;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final List<String> color;
    String track;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "color - {0}")
    public static Object[][] colorsData() {
        return new Object[][]{
                {List.of(Constants.GREY_COLOR)},
                {List.of(Constants.BLACK_COLOR)},
                {List.of(Constants.BLACK_COLOR, Constants.GREY_COLOR)},
                {List.of()},
        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = BaseUrls.QA_SCOOTER_URL;
    }

    @Test
    @DisplayName("Check status code 201 and track in create order with parameterized test")
    public void checkOrderCreateTest() {
        Order order = new Order("Ирина", "Бобарыкина", "Торжковская 15", 5, "89123456789", 5, "2023-04-23", "Позвонить за час до доставки", color);
        Response response = OrderSteps.createOrder(order);
        track = response
                .then()
                .extract()
                .path("track")
                .toString();
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @After
    public void tearDown() {
        OrderSteps.cancelOrder(track);
    }
}
