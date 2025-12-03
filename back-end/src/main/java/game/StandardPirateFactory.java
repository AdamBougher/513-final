package game;

public class StandardPirateFactory extends PirateShipFactory {

    @Override
    public pirateShip createPirateShip(int id, java.awt.Point initialPosition, Board board) {
        pirateShip pirate = new pirateShip(initialPosition.x, initialPosition.y, id, board);
        return pirate;
    }

}
