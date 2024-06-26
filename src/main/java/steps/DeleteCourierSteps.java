package steps;

import static io.restassured.RestAssured.given;

public class DeleteCourierSteps {
    public void deleteCourier(String id){
        if (id != null)
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(id)
                    .when()
                    .delete(BaseUrls.DELETE_COURIER + id);
    }
}
