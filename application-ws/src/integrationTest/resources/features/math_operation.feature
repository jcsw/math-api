Feature: Math calculator

  Scenario: Sum operation
    Given Receive an math operation of SUM with parameters 3.5 and 5.5
    When Do execute POST on /math/operation
    Then I should respond statusCode 200
    And I should respond body {"result":9.0}