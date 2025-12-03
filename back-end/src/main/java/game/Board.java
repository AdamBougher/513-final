package game;

import java.awt.Point;


public class Board {
    private boardCell[][] grid;
    public final int HEIGHT = 25;
    public final int WIDTH = 25;
    public final int ISLAND_COUNT = 10;
    public final int PIRATE_COUNT = 2;
    public final int SEA_MONSTER_COUNT = 1;
    public Ship ship = new Ship(WIDTH/2, HEIGHT/2);
    public pirateShip[] pirates;
    public PirateShipFactory pirateFactory = new StandardPirateFactory();
    public seaMonster[] seaMonsters;

    private final cellState openStates[] = {cellState.WATER, cellState.TREASURE};

    private static Board instance = null;

    private Board(){
        initializeGrid();
    }

    public Board(Board other){
        this.grid = other.grid;
        this.ship = other.ship;
        this.pirates = other.pirates;
        this.pirateFactory = other.pirateFactory;
        this.seaMonsters = other.seaMonsters;
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
        this.grid = new boardCell[WIDTH][HEIGHT];
        // Initialize the board with WATER cells
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                this.grid[i][j] = new boardCell(cellState.WATER, new java.awt.Point(i, j));
            }
        }

        for (int i = 0; i < ISLAND_COUNT; i++) {
            setCell(getRandomOpenCell(), cellState.ISLAND);
        }

        seaMonsters = placeSeaMonsters(SEA_MONSTER_COUNT);

        pirates = new pirateShip[PIRATE_COUNT];

        for(int i = 0; i < PIRATE_COUNT; i++) {
            Point pos = getRandomOpenCell();
            pirates[i] = pirateFactory.createPirateShip(i, pos, this);
            setCell(new Point(pos.x, pos.y), cellState.PIRATE);
            ship.addObserver(pirates[i]);
        }

        setCell(ship.getPosition(), cellState.SHIP);

        setCell(getRandomOpenCell(), cellState.TREASURE);
    }

    public boardCell getCell(int x, int y) {
        return grid[x][y];
    }

    public Boolean isCellOpen(Point position) {

        if (!isValidPosition(position.x, position.y)) {
            return false;
        }

        return java.util.Arrays.asList(openStates).contains(grid[position.x][position.y].getState());
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
        return (x >= 0 && x < HEIGHT) && (y >= 0 && y < WIDTH);
    }

    public boolean isValidPosition(Point p) {
        return (p.x >= 0 && p.x < HEIGHT) && (p.y >= 0 && p.y < WIDTH);
    }

    private seaMonster[] placeSeaMonsters(int amt){
        Point pos;
        seaMonster[] s = new seaMonster[amt];
        
        for (int i = 0; i < amt; i++) {
            pos = getRandomOpenCell();
            s[i] = new seaMonster(i, pos, new bigCircle(this), this);

            ship.addObserver(s[i]);

            setCell(new Point(pos.x, pos.y), cellState.SEA_MONSTER);
        }

        return s;
    }   

    private Point getRandomOpenCell(){
        Point pos;

        do { 
            pos = new Point((int)(Math.random() * HEIGHT), (int)(Math.random() * WIDTH));
        } while (grid[pos.x][pos.y].getState() != cellState.WATER);

        return pos;
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

}
