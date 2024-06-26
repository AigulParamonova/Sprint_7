import serializer.Login;
import steps.BaseUrls;
import steps.CourierSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.DeleteCourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.isA;

public class LoginCourierTest extends DeleteCourierSteps {
    CourierSteps courierSteps;
    String id;
    @Before
    public void setUp() {
        RestAssured.baseURI = BaseUrls.QA_SCOOTER_URL;
        courierSteps = new CourierSteps();
    }

    @Test
    @DisplayName("check login courier test")
    public void checkCourierLoginTest() {
        Login login = new Login("java", "expert");
        Response response = courierSteps.loginCourier(login);

        response
                .then()
                .assertThat()
                .body("id", isA(Integer.class))
                .and()
                .statusCode(200);

        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("check courier login with invalid password")
    public void checkCourierLoginWithInvalidPassword() {
        Login login = new Login("java", "invalid");
        Response response = courierSteps.loginCourier(login);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("check courier login without password")
    public void checkCourierLoginWithoutPassword() {
        Login login = new Login("java", "");
        Response response = courierSteps.loginCourier(login);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

        System.out.println(response.body().asString());

    }

    @After
    public void tearDown(){
        deleteCourier(id);
    }
}
