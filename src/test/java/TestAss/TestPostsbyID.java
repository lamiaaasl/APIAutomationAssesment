package TestAss;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TestPostsbyID {
	

 RequestSpecification requestspec;

	
	@BeforeClass
	public void setupRequestSpecification()
	{
		requestspec = new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com").build();

	}
	
	
	@DataProvider(name = "PostsTestData")
	public String[][] PostsTestData() {
			
		return new String[][] {
			{"1","sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
				,"quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"},
		};
	}
	
	@Test(dataProvider = "PostsTestData")
    public void validateDataInResponeBody(String ID ,String title, String body)
    {
        given().pathParam("id",ID).
        spec(requestspec).when().get("posts/{id}").
                then().assertThat().body("body", equalTo(body));
       
        
    }
    
  @Test
  public void checkStatusCode_expectHttp200() {
	  
    given().spec(requestspec).when().get("posts/1").then().assertThat().statusCode(200);

	  
  }
  
  @Test
  public void checkContentType_PostsAPI() {

      given().
      when().spec(requestspec).get("posts/1").
      then().
          assertThat().
          contentType(ContentType.JSON);
  }
  
  
  @Test
  public void ChecklogRequestAndResponseDetails() {

      given().
          log().all().
      when().spec(requestspec).get("posts/1").
      then().
          log().body();
  }
    
  @Test
  public void extractData()
  {
      String title =
              given().spec(requestspec).when().spec(requestspec).get("posts/1").
                      then().extract().path("title");
      Assert.assertEquals(title,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
  }
  

}
