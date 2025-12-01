package game;

import java.awt.Point;


public class Board implements  Cloneable {
    private boardCell[][] grid;
    public final int ROWS = 10;
    public final int COLS = 50;
    public Ship ship = new Ship(5,5);
    public pirateShip[] pirates;

    private static Board instance = null;

    private Board(){
        initializeGrid();
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public void NewGame() {
        initializeGrid();
    }

    public void initializeGrid() {
        this.grid = new boardCell[ROWS][COLS];
        // Initialize the board with WATER cells
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                this.grid[i][j] = new boardCell(cellState.WATER, new java.awt.Point(i, j));
            }
        }
        placeIslands(10);
        pirates = placePirates(2);

        setCell(ship.getPosition(), cellState.SHIP);
    }

    public boardCell getCell(int x, int y) {
        return grid[x][y];
    }

    public Boolean isCellOpen(Point position) {
        if (!isValidPosition(position.x, position.y)) {
            return false;
        }

        return grid[position.x][position.y].getState() == cellState.WATER;
    }

    public void setCell(Point position, cellState state) {
        if (isValidPosition(position.x, position.y)) {
            grid[position.x][position.y].setState(state);
        }
    }

    public void clearCell(Point position) {
        setCell(position, cellState.WATER);
    }

    public boolean isValidPosition(int x, int y) {
        return (x >= 0 && x < ROWS) && (y >= 0 && y < COLS);
    }

    private void placeIslands(int amt){
        int x  = (int)(Math.random() * ROWS);
        int y = (int)(Math.random() * COLS);
        
        for (int i = 0; i < amt; i++) {
            while (grid[x][y].getState() != cellState.WATER) {
                x = (int)(Math.random() * ROWS);
                y = (int)(Math.random() * COLS);
            }
            setCell(new Point(x, y), cellState.ISLAND);
        }
    }

    private pirateShip [] placePirates(int amt){
        int x  = (int)(Math.random() * ROWS);
        int y = (int)(Math.random() * COLS);
        pirateShip[] p = new pirateShip[amt];
        
        for (int i = 0; i < amt; i++) {
            while (grid[x][y].getState() != cellState.WATER) {
                x = (int)(Math.random() * ROWS);
                y = (int)(Math.random() * COLS);
            }
            p[i] = new pirateShip(x, y, i, this);

            ship.addObserver(p[i]);

            setCell(new Point(x, y), cellState.PIRATE);
        }

        return p;
    }   

    public Ship getColumbus() {
        return ship;
    }

    public Point[] getPiratePositions() {
        Point[] positions = new Point[pirates.length];
        for (int i = 0; i < pirates.length; i++) {
            positions[i] = pirates[i].getPosition();
        }
        return positions;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board clonedBoard = (Board) super.clone();
        clonedBoard.grid = new boardCell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                boardCell originalCell = this.grid[i][j];
                clonedBoard.grid[i][j] = new boardCell(originalCell.getState(), new Point(originalCell.getPosition()));
            }
        }
        // Clone ship
        clonedBoard.ship = new Ship(this.ship.getPosition().x, this.ship.getPosition().y);
        // Clone pirates
        clonedBoard.pirates = new pirateShip[this.pirates.length];
        for (int i = 0; i < this.pirates.length; i++) {
            Point pos = this.pirates[i].getPosition();
            clonedBoard.pirates[i] = new pirateShip(pos.x, pos.y, this.pirates[i].ID, clonedBoard);
            clonedBoard.ship.addObserver(clonedBoard.pirates[i]);
        }
        return clonedBoard;
    }

}
