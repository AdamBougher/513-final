package game;

import java.awt.Point;
import java.util.ArrayList;

import game.interfaces.Direction;
import game.interfaces.pirateStratigy;

public class moveTowardsShipNextPos implements pirateStratigy {
    private ArrayList<boardCell> history;

    @Override
    public Direction moveTowardsShip(Point shipPosition, Point piratePosition, java.util.ArrayList<boardCell> history) {
        this.history = history;
        int shipNextX = shipPosition.x;
        int shipNextY = shipPosition.y+1;
        
        if (Math.abs(shipNextY - piratePosition.y) > Math.abs(shipNextX - piratePosition.x)) {
            // if vertical distance is greater, move vertically
            if (shipNextY < piratePosition.y) {
                return TryMove(new char[] {'U','R', 'D', 'L'}, piratePosition);
            }

            if (shipNextY > piratePosition.y) {
                return TryMove(new char[] {'D','L', 'U', 'R'}, piratePosition);
            }
        }else{
            // if horizontal distance is greater, move horizontally
            if (shipNextX < piratePosition.x) {
                return TryMove(new char[] {'L','U', 'R', 'D'}, piratePosition);
            }
            
            if (shipNextX > piratePosition.x) {
                return TryMove(new char[] {'R','D', 'L', 'U'}, piratePosition);
            }
        }
        return Direction.DOWN; // No movement needed
    }

    private Direction TryMove(char[] dirPriority, Point piratePosition) {
        for(char dir : dirPriority) {
            switch (dir) {
                case 'U' -> {
                    if(
                        Board.getInstance().isCellOpen(new Point(piratePosition.x, piratePosition.y - 1)) && 
                        !history.get(history.size() - 1).getPosition().equals(new Point(piratePosition.x, piratePosition.y - 1))
                    )
                    return Direction.UP;
                }
                case 'D' -> {
                    if(
                        Board.getInstance().isCellOpen(new Point(piratePosition.x, piratePosition.y + 1)) && 
                        !history.get(history.size() - 1).getPosition().equals(new Point(piratePosition.x, piratePosition.y + 1))
                    )
                    return Direction.DOWN;
                }
                case 'L' -> {
                    if(
                        Board.getInstance().isCellOpen(new Point(piratePosition.x - 1, piratePosition.y)) && 
                        !history.get(history.size() - 1).getPosition().equals(new Point(piratePosition.x - 1, piratePosition.y))
                    )
                    return Direction.LEFT;
                }
                case 'R' -> {
                    if(
                        Board.getInstance().isCellOpen(new Point(piratePosition.x + 1, piratePosition.y)) && 
                        !history.get(history.size() - 1).getPosition().equals(new Point(piratePosition.x + 1, piratePosition.y))
                    )
                    return Direction.RIGHT;
                }
            }
        }
        return null;
    }

}
