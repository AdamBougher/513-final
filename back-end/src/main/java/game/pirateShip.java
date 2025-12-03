package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import game.interfaces.Enemy;
import game.interfaces.pirateStratigy;

public class pirateShip extends Enemy implements Observer {

    private pirateStratigy strategy;

    public pirateShip(Point position, int ID, pirateStratigy strategy, Board board){
        this.ID = ID;
        this.position = new Point(position.x, position.y);
        this.strategy = strategy;
        this.history = new ArrayList<>();
        history.add(board.getCell(position.x, position.y));
        this.board = board;
    }

    public pirateStratigy getStrategy() {
        return strategy;
    }

    @Override
    public void update(Observable o, Object arg) {
        int[] shipPosition = (int[]) arg;

        board.clearCell(position);

        Move(strategy.moveTowardsShip(new Point(shipPosition[0], shipPosition[1]), position, history));

        if(position.x == shipPosition[0] && position.y == shipPosition[1]) {
            GameState.state = GameStates.LOST_GAME;
        }

        board.setCell(position, cellState.PIRATE);
        
        history.add(board.getCell(position.x, position.y));
    }


}
