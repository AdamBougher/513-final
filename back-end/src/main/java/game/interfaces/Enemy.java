package game.interfaces;

import java.awt.Point;
import java.util.ArrayList;

import game.Board;
import game.boardCell;
import game.cellState;

public abstract class Enemy implements moveable {
    public     int ID;
    protected  Point position;
    protected  ArrayList<boardCell> history;
    protected  Board board;
    protected cellState[] accepStates = {cellState.WATER, cellState.SHIP, cellState.TREASURE};

    protected void Move(Direction dir) {
        switch(dir){
            case UP -> // North
                goNorth(new boardCell(cellState.WATER, position));
            case DOWN -> // South
                goSouth(new boardCell(cellState.WATER, position));
            case RIGHT -> // East
                goEast(new boardCell(cellState.WATER, position));
            case LEFT -> // West
                goWest(new boardCell(cellState.WATER, position));
            default -> throw new IllegalArgumentException("Unexpected value: " + dir);
        }
    }

    @Override
    public void goNorth(boardCell goToState) {
        if (java.util.Arrays.asList(accepStates).contains(goToState.getState()))
            position.y -= 1;
    }

    @Override
    public void goSouth(boardCell goToState) {
        if (java.util.Arrays.asList(accepStates).contains(goToState.getState()))
            position.y += 1;
    }

    @Override
    public void goEast(boardCell goToState) {
        if (java.util.Arrays.asList(accepStates).contains(goToState.getState()))
            position.x += 1;
    }

    @Override
    public void goWest(boardCell goToState) {
        if (java.util.Arrays.asList(accepStates).contains(goToState.getState()))
            position.x -= 1;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}
