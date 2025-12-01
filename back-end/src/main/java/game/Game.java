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

        try {
            newBoard = (Board)board.clone();
        } catch (CloneNotSupportedException ex) {
            System.err.println("Cloning not supported: " + ex.getMessage());
            return this; // Return unchanged game on failure
        }

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
        if (newX < 0 || newX >= newBoard.ROWS || newY < 0 || newY >= newBoard.COLS) {
            return this; // Out of bounds, return unchanged
        }
        
        boardCell targetCell = newBoard.getCell(newX, newY);
        
        // Clear old ship position
        newBoard.clearCell(ship.getPosition());

        // Update pirates before moving ship
        // Update pirate positions (they chase the ship via Observer pattern)
        // Pirates automatically update via ship.notifyObservers()
        Point[] oldPiratePositions = new Point[newBoard.pirates.length];
        for (int i = 0; i < newBoard.pirates.length; i++) {
            oldPiratePositions[i] = new Point(newBoard.pirates[i].getPosition());
        }

        // Clear old pirate cells using saved positions
        for (Point oldPos : oldPiratePositions) {
            newBoard.clearCell(oldPos);
        }
        
        // Move ship using its interface method
        switch (direction) {
            case UP -> ship.goNorth(targetCell);
            case DOWN -> ship.goSouth(targetCell);
            case LEFT -> ship.goWest(targetCell);
            case RIGHT -> ship.goEast(targetCell);
        }
        
        // Update ship position on board
        newBoard.setCell(ship.getPosition(), cellState.SHIP);

        // Pirates move in their update() method, now update board
        for (pirateShip pirate : newBoard.pirates) {
            newBoard.setCell(pirate.getPosition(), cellState.PIRATE);
        }
        
        return new Game(newBoard, newHistory);
    }

    public Game undo() {
        if (history.isEmpty()) return this;
        return history.get(history.size() - 1);
    }
    

    public boolean isGameOver() {
        return false; // Placeholder for actual game over logic
    }
}
