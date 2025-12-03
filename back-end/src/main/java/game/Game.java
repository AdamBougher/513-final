package game;

import java.util.ArrayList;
import java.util.List;

import game.interfaces.Direction;


public class Game {
    private Board board;
    private final List<Game> history;
    public boolean hasWon = false;

    public Game() {
        this.board = Board.getInstance();
        this.history = new ArrayList<>();
    }

    public Game(Board board) {
        this(board, List.of());
    }

    public Game(Board board, List<Game> history) {
        this.board = board;
        this.history = history;
    }

    public Board getBoard() {
        return this.board;
    }

    public Game move(Direction direction) {
        if (GameState.state != GameStates.ONGOING || hasWon) {
            return null;
        }

        // Save current state for undo
        List<Game> newHistory = new ArrayList<>(history);
        newHistory.add(this);
        
        // Get target cell
        int newX = board.ship.getPosition().x;
        int newY = board.ship.getPosition().y;
        
        switch (direction) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
        }
        
        // Check bounds
        if (newX < 0 || newX >= board.HEIGHT || newY < 0 || newY >= board.WIDTH) {
            return this; // Out of bounds, return unchanged
        }
        
        boardCell targetCell = board.getCell(newX, newY);
        
        // Clear old ship position
        board.clearCell(board.ship.getPosition());
        
        // Move ship using its interface metho
        switch (direction) {
            case UP -> board.ship.goNorth(targetCell);
            case DOWN -> board.ship.goSouth(targetCell);
            case LEFT -> board.ship.goWest(targetCell);
            case RIGHT -> board.ship.goEast(targetCell);
        }

        if(targetCell.getState() == cellState.TREASURE){
            GameState.state = GameStates.WIN_GAME;
        }
        
        // Update ship position on board
        board.setCell(board.ship.getPosition(), cellState.SHIP);
        
        return new Game(board, newHistory);
    }

    public Game undo() {
        if (history.isEmpty()) return this;
        return history.get(history.size() - 1);
    }
    

    
}
