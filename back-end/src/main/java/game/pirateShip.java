package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import game.interfaces.pirateStratigy;

public class pirateShip extends Enemy implements Observer {

    private pirateStratigy strategy;

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


}
