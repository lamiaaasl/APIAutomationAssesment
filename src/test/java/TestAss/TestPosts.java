package TestAss;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TestPosts {
	

 RequestSpecification requestspec;

	
	@BeforeClass
	public void setupRequestSpecification()
	{
		requestspec = new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com").build();

	}
	
    
  @Test
  public void checkStatusCode_expectHttp200() {
	  
    given().spec(requestspec).when().get("posts").then().assertThat().statusCode(200);

	  
  }
  
  @Test
  public void checkContentType_PostsAPI() {

      given().
      when().spec(requestspec).get("posts").
      then().
          assertThat().
          contentType(ContentType.JSON);
  }
  
  
  @Test
  public void ChecklogRequestAndResponseDetails() {

      given().
          log().all().
      when().spec(requestspec).get("posts").
      then().
          log().body();
  }
    
  @Test
  public void checkNumberOfPostsIDsInResponseBody_expect100() {

      given().
      when().spec(requestspec).get("posts").
        
      then().
          assertThat().
          body("id", hasSize(100));
  }
  
  @Test
  public void extractData()
  {
      String title =
              given().spec(requestspec).when().spec(requestspec).get("posts/100").
                      then().extract().path("title");
      Assert.assertEquals(title,"at nam consequatur ea labore ea harum");
  }

}
