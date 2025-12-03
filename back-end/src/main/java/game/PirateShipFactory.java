package game;

import java.awt.Point;

import game.interfaces.pirateStratigy;

public abstract class PirateShipFactory {

    public abstract pirateShip createPirateShip(int id, Point initialPosition, pirateStratigy strategy, Board board);

}
