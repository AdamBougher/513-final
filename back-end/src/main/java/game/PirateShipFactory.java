package game;

import java.awt.Point;

public abstract class PirateShipFactory {

    public abstract pirateShip createPirateShip(int id, Point initialPosition, Board board);

}
