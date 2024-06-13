package postcall;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reusableMethod.Reusable;

import static io.restassured.RestAssured.given;

public class PostCallCheck {

    Reusable r;

    @BeforeClass
    public void setUp(){

        r = new Reusable();
    }

    @Test(priority = 0)
    public void validateXlData(){
        r.readAndPrintDataFromExcel();
    }

    @Parameters({"post_url", "id", "petName","expectedStatusCode"})
    @Test(priority = 1)
    public void createPetId(String url,String id,String petName,String expectedStatusCode){

        Response response = given()
                .contentType(ContentType.JSON)
                .body(r.CreateJsonBody(id,petName))
                .when()
                .post(url);

        int statuscode = response.getStatusCode();
        System.out.println(statuscode);
        Assert.assertEquals(String.valueOf(statuscode),expectedStatusCode);
        String responseBody = response.getBody().asString().toString();
        System.out.println(responseBody);
        Assert.assertTrue(responseBody.contains("available"));
    }

    //Validate petID
    @Parameters({"url","petId"})
    @Test(priority = 2)
    public void validatePetId(String url, String petId){
        Response response = RestAssured.get(url + petId);
        String responseBody = response.getBody().asPrettyString();
        System.out.println(responseBody);
        String responseId =  response.jsonPath().getString("id");
        String responseName = response.jsonPath().getString("name");
        System.out.println("PetId is:"+responseId+ " Pet name is:" +responseName);
    }

    @Parameters({"url","petId"})
    @Test(priority = 3)
    public void deletePetId(String url,String petId) {
        Response response = RestAssured.delete(url + petId);
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();
        System.out.println(responseBody);
        if (statusCode == 200) {
            System.out.println("Pet deleted successfully.");
        } else {
            System.out.println("Failed to delete pet. Status code: " + statusCode);
        }
    }
}
