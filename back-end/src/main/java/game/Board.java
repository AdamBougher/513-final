package game;

import java.awt.Point;
import java.util.ArrayList;

import game.interfaces.pirateStratigy;
import game.stratagies.bigCircle;
import game.stratagies.moveDirectlyTowardsShip;
import game.stratagies.moveTowardsShipNextPos;

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

    public static void resetInstance() {
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
            pirateShip newPirate = new pirateShip(pos, p.ID, p.getStrategy(), this);

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

    public boardCell[][] getGrid() {
        return grid;
    }

    // Starts a new game by re-initializing the grid
    public void NewGame() {
        initializeGrid();
    }

    // Initializes the game board with default values
    private void initializeGrid() {

        this.grid = new boardCell[WIDTH][HEIGHT];
        // Initialize the board with WATER cells
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                this.grid[i][j] = new boardCell(cellState.WATER, new Point(i, j));
            }
        }

        this.ship.deleteObservers();
        this.ship = new Ship(WIDTH/2, HEIGHT/2);

        setCell(new Point(WIDTH/2, HEIGHT/2), cellState.SHIP);

        for (int i = 0; i < ISLAND_COUNT; i++) {
            setCell(getRandomOpenCell(0), cellState.ISLAND);
        }
        
        pirateFactories = new ArrayList<>();
        pirateFactories.add(new StandardPirateFactory());

        pirateStratigy[] strategies = {
            new moveDirectlyTowardsShip(),
            new moveTowardsShipNextPos()
        };

        this.pirates = new ArrayList<>();

        for(int i = 0; i < PIRATE_COUNT; i++) {
            pirateShip pirate = pirateFactories.get((int)(Math.random() * pirateFactories.size())).createPirateShip(i, strategies[(int)(Math.random() * strategies.length)], this);
            pirates.add(pirate);
            setCell(pirate.getPosition(), cellState.PIRATE);
            ship.addObserver(pirates.get(i));
        }

        // Place sea monsters
        this.seaMonsters = placeSeaMonsters(SEA_MONSTER_COUNT);
        
        
        // Place the treasure
        setCell(getRandomOpenCell(0), cellState.TREASURE);
    }

    // Get the cell at the specified coordinates
    public boardCell getCell(int x, int y) {
        return grid[x][y];
    }

    // Check if a cell is open (WATER or TREASURE)
    public Boolean isCellOpen(Point position) {

        if (!isValidPosition(position.x, position.y)) {
            return false;
        }

        return java.util.Arrays.asList(openStates).contains(grid[position.x][position.y].getState());
    }

    // Set the state of a cell at the specified coordinates
    public void setCell(Point position, cellState state) {
        if (isValidPosition(position.x, position.y)) {
            grid[position.x][position.y].setState(state);
        }
    }

    // Clear the cell at the specified coordinates (set to WATER)
    public void clearCell(Point position) {
        setCell(position, cellState.WATER);
    }

    // Check if the position is within the bounds of the board
    public boolean isValidPosition(int x, int y) {
        return (x >= 0 && x < HEIGHT) && (y >= 0 && y < WIDTH);
    }

    public boolean isValidPosition(Point p) {
        return (p.x >= 0 && p.x < HEIGHT) && (p.y >= 0 && p.y < WIDTH);
    }
    
    // Place sea monsters on the board
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
    
    // Get a random open cell on the board, with an optional offset for range
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

    // Get the Columbus ship
    public Ship getColumbus() {
        return ship;
    }
    
    // Get the positions of all pirate ships
    public Point[] getPiratePositions() {
        Point[] positions = new Point[pirates.size()];
        for (int i = 0; i < pirates.size(); i++) {
            positions[i] = pirates.get(i).getPosition();
        }
        return positions;
    }

}
