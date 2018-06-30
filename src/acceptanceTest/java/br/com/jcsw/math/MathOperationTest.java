package br.com.jcsw.math;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RunWith(JUnit4.class)
public class MathOperationTest {

  private static final String URL = "http://localhost:9900/math/operation";
  private static final String REQUEST = "{\"operation\": \"%s\", \"parameters\": [%s, %s]}";
  private static final String RESPONSE = "{\"result\":%s}";

  @Test
  public void shouldReturnOperationResult() {

    String operation = "SUM";
    String firstParameter = "1.0";
    String secondParameter = "1.5";

    String expectedResponse = "2.5";

    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(String.format(REQUEST, operation, firstParameter, secondParameter))
        .when()
        .post(URL)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(String.format(RESPONSE, expectedResponse)));


  }

}
