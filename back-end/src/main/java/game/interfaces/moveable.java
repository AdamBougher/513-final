package game.interfaces;

import java.awt.Point;

import game.boardCell;

public interface moveable {

    void goNorth(boardCell goToState);
    void goSouth(boardCell goToState);
    void goEast(boardCell goToState);
    void goWest(boardCell goToState);
    public Point getPosition();
}
