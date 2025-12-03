package game;

import java.awt.Point;
import java.util.Observable;

import game.interfaces.moveable;

public class Ship extends Observable implements moveable {
    private Point position;

    public Ship(int x, int y) {
        this.position = new Point(x, y);
    }

    @Override
    public void goNorth(boardCell goToState) {
        if (Board.getInstance().isCellOpen(new Point(position.x, position.y - 1)))
            position.y -= 1;
        notifyObservers();
    }

    @Override
    public void goSouth(boardCell goToState) {
        if (Board.getInstance().isCellOpen(new Point(position.x, position.y + 1)))
            position.y += 1;
        notifyObservers();
    }

    @Override
    public void goEast(boardCell goToState) {
        if (Board.getInstance().isCellOpen(new Point(position.x + 1, position.y)))
            position.x += 1;
        notifyObservers();
    }

    @Override
    public void goWest(boardCell goToState) {
        if (Board.getInstance().isCellOpen(new Point(position.x - 1, position.y)))
            position.x -= 1;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        int[] position = { this.position.x, this.position.y };
        setChanged();
        super.notifyObservers(position);
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

}
