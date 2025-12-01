package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import game.interfaces.Direction;
import game.interfaces.moveable;
import game.interfaces.pirateStratigy;

public class pirateShip implements Observer, moveable {

    public int ID;
    private Point position;
    private pirateStratigy strategy;
    private ArrayList<boardCell> history;
    private Board board;

    public pirateShip(int x, int y, int ID, Board board){
        this.ID = ID;
        this.position = new Point(x, y);
        this.strategy = new moveDirectlyTowardsShip();
        this.history = new ArrayList<>();
        history.add(board.getCell(position.x, position.y));
        this.board = board;
    }

    @Override
    public void update(Observable o, Object arg) {
        int[] shipPosition = (int[]) arg;
        int shipX = shipPosition[0];
        int shipY = shipPosition[1];

        int x = this.position.x;
        int y = this.position.y;

        Move(strategy.moveTowardsShip(new Point(shipX, shipY), position, history));

        history.add(board.getCell(position.x, position.y));

    }

    private void Move(Direction dir) {
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
