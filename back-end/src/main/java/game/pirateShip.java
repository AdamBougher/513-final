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

        board.clearCell(position);

        Move(strategy.moveTowardsShip(new Point(shipPosition[0], shipPosition[1]), position, history));

        board.setCell(position, cellState.PIRATE);
        
        history.add(board.getCell(position.x, position.y));
    }


}
