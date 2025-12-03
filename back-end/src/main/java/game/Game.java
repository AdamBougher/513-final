package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.interfaces.Direction;


public class Game {
    private Board board;
    private final List<Game> history;

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
        // Save current state for undo
        List<Game> newHistory = new ArrayList<>(history);
        newHistory.add(this);
        
        // Create deep copy of board for immutability
        Board newBoard;

        newBoard = new Board(this.board);
  
        Ship ship = newBoard.ship;
        
        // Get target cell
        int newX = ship.getPosition().x;
        int newY = ship.getPosition().y;
        
        switch (direction) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
        }
        
        // Check bounds
        if (newX < 0 || newX >= newBoard.HEIGHT || newY < 0 || newY >= newBoard.WIDTH) {
            return this; // Out of bounds, return unchanged
        }
        
        boardCell targetCell = newBoard.getCell(newX, newY);
        
        // Clear old ship position
        newBoard.clearCell(ship.getPosition());
        
        // Move ship using its interface metho
        switch (direction) {
            case UP -> ship.goNorth(targetCell);
            case DOWN -> ship.goSouth(targetCell);
            case LEFT -> ship.goWest(targetCell);
            case RIGHT -> ship.goEast(targetCell);
        }

        ship.checkWin(new Point(newX, newY));
        
        // Update ship position on board
        newBoard.setCell(ship.getPosition(), cellState.SHIP);
        
        return new Game(newBoard, newHistory);
    }

    public Game undo() {
        if (history.isEmpty()) return this;
        return history.get(history.size() - 1);
    }
    

    
}
