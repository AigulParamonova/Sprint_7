package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serializer.Courier;
import serializer.Login;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Create a courier")
    public Response createCourier(Courier courier) {
        return  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BaseUrls.COURIER);
    }


    @Step("Login a courier")
    public Response loginCourier(Login login) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post(BaseUrls.LOGIN_COURIER);
    }
}
