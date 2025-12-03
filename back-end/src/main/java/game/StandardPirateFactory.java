package game;

import game.interfaces.pirateStratigy;

public class StandardPirateFactory extends PirateShipFactory {

    @Override
    public pirateShip createPirateShip(int id, java.awt.Point initialPosition, pirateStratigy strategy, Board board) {
        pirateShip pirate = new pirateShip(initialPosition.x, initialPosition.y, id, strategy, board);
        return pirate;
    }

}
