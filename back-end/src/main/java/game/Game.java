package game;

import java.util.ArrayList;
import java.util.List;

import game.interfaces.Direction;


public class Game {
    private final Board board;
    private final List<Game> history;
    public boolean hasWon = false;

    public Game() {
        this.board = Board.getInstance();
        this.history = new ArrayList<>();
    }

    public Game(Board board, List<Game> history) {
        this.board = board;
        this.history = history;
    }

    public Board getBoard() {
        return this.board;
    }

    public Game move(Direction direction) {
        // Save current state for undo
        List<Game> newHistory = new ArrayList<>(history);
        newHistory.add(this);

        Board newBoard = new Board(board);
        
        // Get target cell
        int newX = newBoard.ship.getPosition().x;
        int newY = newBoard.ship.getPosition().y;
        
        // Calculate new position based on direction
        switch (direction) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        }
        
        // Check bounds of the newBoard
        if (newX < 0 || newX >= newBoard.HEIGHT || newY < 0 || newY >= newBoard.WIDTH) {
            return this;
        }
        
        boardCell targetCell = newBoard.getCell(newX, newY);
        
        // Clear old ship position
        newBoard.clearCell(newBoard.ship.getPosition());
        
        // Move ship using its interface metho
        switch (direction) {
            case UP -> newBoard.ship.goNorth(targetCell);
            case DOWN -> newBoard.ship.goSouth(targetCell);
            case LEFT -> newBoard.ship.goWest(targetCell);
            case RIGHT -> newBoard.ship.goEast(targetCell);
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        }

        // Check for win condition
        if(targetCell.getState() == cellState.TREASURE){
            GameState.state = GameStates.WIN_GAME;
        }
        
        // Update ship position on newBoard
        newBoard.setCell(newBoard.ship.getPosition(), cellState.SHIP);
        
        return new Game(newBoard, newHistory);
    }

    public Game undo() {
        if (history.isEmpty()) return this;
        return history.get(history.size() - 1);
    }
    

    
}
