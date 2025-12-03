package game;

import java.awt.Point;
import java.util.ArrayList;

import game.interfaces.pirateStratigy;

public class Board {
    private boardCell[][] grid;
    public final int HEIGHT = 25;
    public final int WIDTH = 25;
    public final int ISLAND_COUNT = 20;
    public final int PIRATE_COUNT = 2;
    public final int SEA_MONSTER_COUNT = 1;
    public Ship ship = new Ship(WIDTH/2, HEIGHT/2);
    public ArrayList<pirateShip> pirates;
    public ArrayList<PirateShipFactory> pirateFactories;
    public ArrayList<seaMonster> seaMonsters;

    private final cellState openStates[] = {cellState.WATER, cellState.TREASURE};

    private static Board instance = null;

    private Board(){
        initializeGrid();
    }

    public void resetInstance() {
        instance = null;
    }

    public Board(Board other){
        this.grid = new boardCell[other.HEIGHT][other.WIDTH];
        for(boardCell[] row : other.grid){
            for(boardCell cell : row){
                this.grid[cell.getPosition().x][cell.getPosition().y] = new boardCell(cell.getState(), new Point(cell.getPosition().x, cell.getPosition().y));
            }
        }

        this.ship = new Ship(other.ship.getPosition().x, other.ship.getPosition().y);

        this.pirates = new ArrayList<>();
        for(pirateShip p : other.pirates){
            Point pos = p.getPosition();
            pirateShip newPirate = new pirateShip(p.ID, new Point(pos.x, pos.y), this);
            this.pirates.add(newPirate);
            this.ship.addObserver(newPirate);
        }

        this.pirateFactories = other.pirateFactories;

        this.seaMonsters = new ArrayList<>();
        for(seaMonster s : other.seaMonsters){
            Point pos = s.getPosition();
            seaMonster newSeaMonster = new seaMonster(s.ID, new Point(pos.x, pos.y), new bigCircle(this), this);
            this.seaMonsters.add(newSeaMonster);
            this.ship.addObserver(newSeaMonster);
        }
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
        ship.deleteObservers();

        this.grid = new boardCell[WIDTH][HEIGHT];
        // Initialize the board with WATER cells
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                this.grid[i][j] = new boardCell(cellState.WATER, new java.awt.Point(i, j));
            }
        }

        for (int i = 0; i < ISLAND_COUNT; i++) {
            setCell(getRandomOpenCell(0), cellState.ISLAND);
        }
        
        pirateFactories = new ArrayList<>();
        pirateFactories.add(new StandardPirateFactory());
        seaMonsters = placeSeaMonsters(SEA_MONSTER_COUNT);

        pirates = new ArrayList<>();

        pirateStratigy[] strategies = {
            new moveDirectlyTowardsShip(),
            new moveTowardsShipNextPos()
        };

        for(int i = 0; i < PIRATE_COUNT; i++) {
            Point pos = getRandomOpenCell(0);
            pirates.add(pirateFactories.get(0).createPirateShip(i, pos, strategies[(int)(Math.random() * strategies.length)], this));
            setCell(new Point(pos.x, pos.y), cellState.PIRATE);
            ship.addObserver(pirates.get(i));
        }

        setCell(new Point(WIDTH/2, HEIGHT/2), cellState.SHIP);

        setCell(getRandomOpenCell(0), cellState.TREASURE);
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

    private ArrayList<seaMonster> placeSeaMonsters(int amt){
        Point pos;
        ArrayList<seaMonster> s = new ArrayList<>();
        
        for (int i = 0; i < amt; i++) {
            pos = getRandomOpenCell(-5);
            s.add(new seaMonster(i, pos, new bigCircle(this), this));

            ship.addObserver(s.get(i));

            setCell(new Point(pos.x, pos.y), cellState.SEA_MONSTER);
        }

        return s;
    }   

    private Point getRandomOpenCell(int offset){
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

    public Ship getColumbus() {
        return ship;
    }

    public Point[] getPiratePositions() {
        Point[] positions = new Point[pirates.size()];
        for (int i = 0; i < pirates.size(); i++) {
            positions[i] = pirates.get(i).getPosition();
        }
        return positions;
    }

}
