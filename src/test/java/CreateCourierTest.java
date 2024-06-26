import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import serializer.Courier;
import steps.BaseUrls;
import steps.CourierSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.DeleteCourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends DeleteCourierSteps{
    CourierSteps courierSteps;
    String id;
    @Before
    public void setUp() {
        RestAssured.baseURI = BaseUrls.QA_SCOOTER_URL;
        courierSteps = new CourierSteps();
    }
    @Test
    @DisplayName("check courier create test")
    public void checkCourierCreateTest() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10));
        Response response = courierSteps.createCourier(courier);
        response
                .then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("check creating two identical couriers")
    public void checkCreateTwoIdenticalCouriers() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10),
                                      RandomStringUtils.randomAlphabetic(10),
                                      RandomStringUtils.randomAlphabetic(10));
        Response createCourier = courierSteps.createCourier(courier);
        createCourier
                .then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);

        Response createSecondCourier = courierSteps.createCourier(courier);
        createSecondCourier
                .then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        System.out.println(createSecondCourier.body().asString());
    }

    @Test
    @DisplayName("check create courier with required fields")
    public void checkCreateCourierWithRequiredFields() {
        Courier courierInvalid = new Courier("white", null, "snow");
        Response response = courierSteps.createCourier(courierInvalid);

        response
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());

    }

    @After
    public void tearDown(){
        deleteCourier(id);
    }
}
