package game;

import java.awt.Point;

import game.interfaces.pirateStratigy;

public abstract class PirateShipFactory {

    public abstract pirateShip createPirateShip(int id, pirateStratigy strategy, Board board);

    protected Point getRandomOpenCell(int HEIGHT, int WIDTH, boardCell[][] grid, int offset) {
        Point pos;
        int hight;
        int width;

        do { 
            hight = (int)(Math.random() * (HEIGHT + offset));
            width = (int)(Math.random() * (WIDTH + offset));
            pos = new Point(hight, width);
        } while (grid[pos.x][pos.y].getState() != cellState.WATER);

        return pos;
    }

}
