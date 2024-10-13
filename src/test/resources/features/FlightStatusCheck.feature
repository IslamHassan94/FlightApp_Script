Feature: Check your flight status

  As a user of the website
  I want to be able to get the flight status for a given route on a specific date
  So that I can plan my travel accordingly

  Background:
    Given the user navigates to the flight status page

  Scenario Outline: Verify flight status by route
    When the user enters "<From>" and "<To>" as the flight route
    And the user selects "<Date>" as the travel date
    And the user clicks the show flight status button
    Then the search results should display flights from "<From>" to "<To>"
    And the results should be for the date "<Date>"
    And the flight status should be displayed

    Examples:
      | From | To  | Date     |
      | CGN  | BER | today    |
      | CGN  | BER | tomorrow |
      | CGN  | BER | past     |


  Scenario Outline: Verify flight status by flight number
    When the user enters "<FlightNumber>" as the flight number
    And the user selects "<Date>" as the travel date
    And the user clicks the show flight status button
    Then the search results should display the route for flight "<FlightNumber>"
    And the results should be for the date "<Date>"
    And the flight status should be displayed

    Examples:
      | FlightNumber | Date     |
      | EW8071       | today    |
      | EW50         | tomorrow |
      | EW18         | past     |

  Scenario Outline: Verify that a message is displayed in case of status not available
    When the user enters "<From>" and "<To>" as the flight route
    And the user selects "<Date>" as the travel date
    And the user clicks the show flight status button
    Then a message is displayed to inform the user

    Examples:
      | From | To      | Date  |
      | CGN  | Albania | today |


  Scenario Outline: Verify that the submit button is disabled if any of the search fields is missing
    When the user enters "<From>" and "<To>" as the flight route
    And the user selects "<Date>" as the travel date
    Then the submit button is disabled

    Examples:
      | From | To  | Date  |
      |      | BER | today |
      | CGN  | BER |       |
      | CGN  |     | today |
