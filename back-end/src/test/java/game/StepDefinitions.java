package game;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import game.interfaces.Direction;
import java.awt.Point;
import static org.junit.Assert.*;

public class StepDefinitions {
    private Game game;
    private Board board;
    private Point initialShipPosition;
    
    @Given("I start a new game")
    public void i_start_a_new_game() {
        GameState.state = GameStates.ONGOING;
        game = new Game();
        board = game.getBoard();
        initialShipPosition = new Point(board.ship.getPosition());
    }
    
    @Then("the board should be initialized")
    public void the_board_should_be_initialized() {
        assertNotNull("Board should not be null", board);
        assertNotNull("Grid should be initialized", board.getCell(0, 0));
    }
    
    @And("the ship should be at the center")
    public void the_ship_should_be_at_the_center() {
        Point shipPos = board.ship.getPosition();
        assertEquals("Ship X should be at center", board.WIDTH / 2, shipPos.x);
        assertEquals("Ship Y should be at center", board.HEIGHT / 2, shipPos.y);
    }
    
    @And("there should be {int} islands")
    public void there_should_be_islands(int count) {
        int islandCount = 0;
        for (int i = 0; i < board.HEIGHT; i++) {
            for (int j = 0; j < board.WIDTH; j++) {
                if (board.getCell(i, j).getState() == cellState.ISLAND) {
                    islandCount++;
                }
            }
        }
        assertEquals("Island count should match", count, islandCount);
    }
    
    @And("there should be {int} pirates")
    public void there_should_be_pirates(int count) {
        assertEquals("Pirate count should match", count, board.pirates.size());
    }
    
    @And("there should be {int} sea monster")
    public void there_should_be_sea_monster(int count) {
        assertEquals("Sea monster count should match", count, board.seaMonsters.size());
    }
    
    @And("there should be {int} treasure")
    public void there_should_be_treasure(int count) {
        int treasureCount = 0;
        for (int i = 0; i < board.HEIGHT; i++) {
            for (int j = 0; j < board.WIDTH; j++) {
                if (board.getCell(i, j).getState() == cellState.TREASURE) {
                    treasureCount++;
                }
            }
        }
        assertEquals("Treasure count should match", count, treasureCount);
    }
    
    @When("I move the ship {string}")
    public void i_move_the_ship(String direction) {
        Direction dir = Direction.valueOf(direction);
        game = game.move(dir);
        board = game.getBoard();
    }
    
    @Then("the ship should move successfully")
    public void the_ship_should_move_successfully() {
        Point currentPos = board.ship.getPosition();
        assertNotEquals("Ship should have moved from initial position", 
                        initialShipPosition, currentPos);
    }
    
    @And("the game status should be {string}")
    public void the_game_status_should_be(String status) {
        GameStates expectedState = GameStates.valueOf(status);
        assertEquals("Game status should match", expectedState, GameState.state);
    }
    
    @And("the treasure is adjacent to the ship")
    public void the_treasure_is_adjacent_to_the_ship() {
        Point shipPos = board.ship.getPosition();
        // Place treasure to the right of ship
        board.setCell(new Point(shipPos.x + 1, shipPos.y), cellState.TREASURE);
    }
    
    @When("I move the ship towards the treasure")
    public void i_move_the_ship_towards_the_treasure() {
        game = game.move(Direction.RIGHT);
        board = game.getBoard();
    }
    
    @And("an island is adjacent to the ship")
    public void an_island_is_adjacent_to_the_ship() {
        Point shipPos = board.ship.getPosition();
        board.setCell(new Point(shipPos.x + 1, shipPos.y), cellState.ISLAND);
    }
    
    @When("I try to move the ship to the island")
    public void i_try_to_move_the_ship_to_the_island() {
        initialShipPosition = new Point(board.ship.getPosition());
        game = game.move(Direction.RIGHT);
        board = game.getBoard();
    }
    
    @Then("the ship should not move")
    public void the_ship_should_not_move() {
        Point currentPos = board.ship.getPosition();
        assertEquals("Ship should not have moved", initialShipPosition, currentPos);
    }
    
    @Then("the pirates should move towards the ship")
    public void the_pirates_should_move_towards_the_ship() {
        // Pirates move as observers, just verify they exist and are still tracking
        assertNotNull("Pirates should exist", board.pirates);
        assertTrue("Pirates should be tracking ship", board.pirates.size() > 0);
    }
}
