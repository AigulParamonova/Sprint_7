import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.BaseUrls;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BaseUrls.QA_SCOOTER_URL;
    }

    @DisplayName("Check orders field")
    @Test
    public void checkOrderFields(){
        Response response =
                given()
                .when()
                .header("Content-type", "application/json")
                .get(BaseUrls.ORDERS);
        response.then().log().all();
        MatcherAssert.assertThat("orders", notNullValue());
    }
}
