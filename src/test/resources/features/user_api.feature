Feature: JSONPlaceholder API Testing

  Scenario: Get a user by ID
    Given I send a GET request to "/users/1"
    Then the response status should be 200
    And the response should contain username "Bret"

  Scenario: Create a new post for a user
    Given I send a POST request to "/posts" with body:
      | title  | body                  | userId |
      | Test   | This is a test post.  | 1      |
    Then the response status should be 201
    And the response should contain the title "Test"
