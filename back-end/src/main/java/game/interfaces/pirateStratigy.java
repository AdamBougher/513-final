package game.interfaces;

import java.awt.Point;
import java.util.ArrayList;

import game.boardCell;

public interface pirateStratigy {
    public Direction moveTowardsShip(Point shipPosition, Point piratePosition, ArrayList<boardCell> history);
}
