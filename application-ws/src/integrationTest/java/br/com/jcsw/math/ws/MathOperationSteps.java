package br.com.jcsw.math.ws;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = TestConfiguration.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public class MathOperationSteps {

  private Response response;
  private RequestSpecification request;

  private static String ENDPOINT_MATH_OPERATION = "http://localhost:9903/math/operation";

  @Given("^Receive an math operation of (.*) with parameters (\\d+\\.\\d+) and (\\d+\\.\\d+)")
  public void receive_operation_and_parameters( //
      String operation, //
      String firstParameter, //
      String secondParameter) {

    request = given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(String
            .format("{\"operation\": \"%s\", \"parameters\": [%s, %s]}", operation, firstParameter, secondParameter));
  }

  @When("^Do execute POST on /math/operation")
  public void do_math_operation_execute() {
    response = request.when().post(ENDPOINT_MATH_OPERATION);
  }

  @Then("^I should respond statusCode (\\d+)")
  public void i_should_respond_status_code(Integer statusCode) {
    response.then().statusCode(statusCode);
  }

  @And("^I should respond body (.*)")
  public void i_should_respond_body(String expectedBody) {
    response.then().assertThat().body(equalTo(expectedBody));
  }

}
