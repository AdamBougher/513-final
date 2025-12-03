package game;

import java.util.Arrays;

public class GameState {

    private final boardCell[] cells;
    public static GameStates state;
    private final String status;

    private GameState(boardCell[] cells, String status) { 
        this.cells = cells;
        this.status = status;
    }

    public static GameState forGame(Game game) {
        switch (state) {
            case ONGOING -> {
                boardCell[] cells = getCells(game);
                return new GameState(cells, "ONGOING");
            }
            case LOST_GAME -> {
                boardCell[] cells = getCells(game);
                return new GameState(cells, "You Lost!");
            }
            case WIN_GAME -> {
                boardCell[] cells = getCells(game);
                return new GameState(cells, "You Won!");
            }
            default -> {
            }
        }
        return null;
    }

    public static GameState WinGame(Game game) {
        boardCell[] cells = getCells(game);
        return new GameState(cells, "You Won!");
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
        boardCell[] cells = new boardCell[board.HEIGHT * board.WIDTH];
        
        for (int x = 0; x < board.HEIGHT; x++) {
            for (int y = 0; y < board.WIDTH; y++) {
                cells[y * board.HEIGHT + x] = board.getCell(x, y);
            }
        }
        return cells;
    }
}