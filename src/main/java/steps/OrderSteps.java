package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import serializer.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("create order")
    public static Response createOrder(Order order) {
        Response response =
                given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(BaseUrls.ORDERS);
        return response;
    }

    @Step("cancel order")
    public static void cancelOrder(String track) {
        if (track != null)
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(track)
                    .when()
                    .delete(BaseUrls.CANCEL_ORDER + track);

    }
}
