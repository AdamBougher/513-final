package game;

import java.awt.Point;
import java.util.ArrayList;

import game.interfaces.Direction;
import game.interfaces.moveable;

public abstract class Enemy implements moveable {
    public     int ID;
    protected  Point position;
    protected  ArrayList<boardCell> history;
    protected  Board board;

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
        }
    }

    @Override
    public void goNorth(boardCell goToState) {
        if (goToState.getState() == cellState.WATER)
            position.y -= 1;
    }

    @Override
    public void goSouth(boardCell goToState) {
        if (goToState.getState() == cellState.WATER)
            position.y += 1;
    }

    @Override
    public void goEast(boardCell goToState) {
        if (goToState.getState() == cellState.WATER)
            position.x += 1;
    }

    @Override
    public void goWest(boardCell goToState) {
        if (goToState.getState() == cellState.WATER)
            position.x -= 1;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}
