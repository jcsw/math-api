Feature: Math calculator

  Scenario: Sum operation
    Given Receive an math operation of SUM with parameters 3.5 and 5.5
    When Do math operation execute
    Then I should respond 9.0

  Scenario: Subtraction operation
    Given Receive an math operation of SUB with parameters 5.5 and 2.5
    When Do math operation execute
    Then I should respond 3.0