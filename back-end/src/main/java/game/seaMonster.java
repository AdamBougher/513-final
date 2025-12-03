package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import game.interfaces.monsterStratigy;

public class seaMonster extends Enemy implements Observer {

    private monsterStratigy strategy;

    public seaMonster(int id, Point startPosition, monsterStratigy strategy, Board board) {
        this.ID = id;
        this.position = startPosition;
        this.strategy = strategy;
        this.history = new ArrayList<>();
        this.board = board;
    }

    @Override      
    public void update(Observable o, Object arg) {
        board.clearCell(position);

        Move(strategy.Move(this.position));

        board.setCell(position, cellState.SEA_MONSTER);

        history.add(board.getCell(position.x, position.y));
    }


}
