Feature: Math calculator

  Scenario: Sum operation
    Given Receive an math operation of SUM with parameters 3.5 and 5.5
    When Do math operation execute
    Then I should respond 9.0