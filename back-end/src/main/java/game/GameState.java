package game;

import java.awt.Point;
import java.util.Arrays;

public class GameState {

    private final boardCell[] cells;
    private final Point shipPosition;
    private String status;

    private GameState(boardCell[] cells, Point shipPosition, String status) { 
        this.cells = cells;
        this.shipPosition = shipPosition;
        this.status = status;
    }

    public static GameState forGame(Game game) {
        boardCell[] cells = getCells(game);
        return new GameState(cells, game.getBoard().ship.getPosition(), "ONGOING");
    }

    public boardCell[] getCells() {
        return this.cells;
    }

    /**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        return """
                { "cells": %s, "status": "%s"}
                """.formatted(Arrays.toString(this.cells), this.status);
    }

    private static boardCell[] getCells(Game game) {
        Board board = game.getBoard();
        boardCell[] cells = new boardCell[board.ROWS * board.COLS];
        
        for (int x = 0; x < board.ROWS; x++) {
            for (int y = 0; y < board.COLS; y++) {
                cells[y * board.ROWS + x] = board.getCell(x, y);
            }
        }
        return cells;
    }
}