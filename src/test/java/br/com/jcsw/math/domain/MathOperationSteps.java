package br.com.jcsw.math.domain;

import br.com.jcsw.math.domain.api.MathOperation;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;

public class MathOperationSteps {

  private MathOperation mathOperation;
  private List<BigDecimal> parameters;

  private BigDecimal operationResult;

  @Given("^Receive an math operation of (.*) with parameters (\\d+\\.\\d+) and (\\d+\\.\\d+)")
  public void receive_operation_and_parameters( //
      String operation, //
      String firstParameter, //
      String secondParameter) {
    mathOperation = MathOperation.valueOf(operation);
    parameters = Arrays.asList(new BigDecimal(firstParameter), new BigDecimal(secondParameter));
  }

  @When("^Do math operation execute")
  public void do_math_operation_execute() {
    operationResult = MathExecutor.execute(mathOperation, parameters);
  }

  @Then("^I should respond (\\d+\\.\\d+)")
  public void i_should_respond(String expectedResult) {
    Assert.assertEquals(new BigDecimal(expectedResult), operationResult);
  }

}
