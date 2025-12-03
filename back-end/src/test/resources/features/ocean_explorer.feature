Feature: Ocean Explorer Game
  As a player
  I want to navigate the ocean and find treasure
  So that I can win the game

  Scenario: Start a new game
    Given I start a new game
    Then the board should be initialized
    And the ship should be at the center
    And there should be 20 islands
    And there should be 2 pirates
    And there should be 1 sea monster
    And there should be 1 treasure

  Scenario: Move ship to valid water cell
    Given I start a new game
    When I move the ship "RIGHT"
    Then the ship should move successfully
    And the game status should be "ONGOING"

  Scenario: Win by reaching treasure
    Given I start a new game
    And the treasure is adjacent to the ship
    When I move the ship towards the treasure
    Then the game status should be "WIN_GAME"

  Scenario: Cannot move to island
    Given I start a new game
    And an island is adjacent to the ship
    When I try to move the ship to the island
    Then the ship should not move

  Scenario: Pirates chase the ship
    Given I start a new game
    When I move the ship "RIGHT"
    Then the pirates should move towards the ship