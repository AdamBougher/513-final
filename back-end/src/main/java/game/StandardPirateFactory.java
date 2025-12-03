package game;

import java.awt.Point;

import game.interfaces.pirateStratigy;

public class StandardPirateFactory extends PirateShipFactory {
    
    @Override
    public pirateShip createPirateShip(int id, pirateStratigy strategy, Board board) {
        Point initialPosition = getRandomOpenCell(board.HEIGHT, board.WIDTH, board.getGrid(), -2);
        pirateShip pirate = new pirateShip(initialPosition, id, strategy, board);
        return pirate;
    }

}
