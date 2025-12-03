package game.stratagies;

import java.awt.Point;

import game.Board;
import game.interfaces.Direction;
import game.interfaces.monsterStratigy;

public class bigCircle implements monsterStratigy {
    
    private  final int TURN_AMT = 4;
    private int cellCount;
    private Direction currentDirection = Direction.RIGHT;
    private Board board;

    public bigCircle(Board board){
        this.board = board;
        this.cellCount = 0;
    }
    
    @Override
    public Direction Move(Point monsterPosition) {
        if(cellCount >= TURN_AMT){
            currentDirection = getNextDirection(currentDirection);
            cellCount = 0;
        }

        Direction nextPosition = getNextPosition(monsterPosition, currentDirection);
        cellCount++;

        return nextPosition;
        
    }

    private Direction getNextPosition(Point currentPosition, Direction direction) {
        Point newPosition = new Point(currentPosition);
        PointDirection pd = null;
        switch (direction) {
            case UP -> pd = new PointDirection(new Point(newPosition.x, newPosition.y - 1), Direction.UP);
            case DOWN -> pd = new PointDirection(new Point(newPosition.x, newPosition.y + 1), Direction.DOWN);
            case LEFT -> pd = new PointDirection(new Point(newPosition.x - 1, newPosition.y), Direction.LEFT);
            case RIGHT -> pd = new PointDirection(new Point(newPosition.x + 1, newPosition.y), Direction.RIGHT);
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        }

        if(pd != null && board.isCellOpen(pd.point)){
            return pd.direction;
        }

        return getNextPosition(currentPosition, getNextDirection(currentDirection));
    }

    private Direction getNextDirection(Direction currentDirection) {
        return switch (currentDirection) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
            case DOWN_LEFT -> throw new UnsupportedOperationException("Unimplemented case: " + currentDirection);
            case DOWN_RIGHT -> throw new UnsupportedOperationException("Unimplemented case: " + currentDirection);
            case UP_LEFT -> throw new UnsupportedOperationException("Unimplemented case: " + currentDirection);
            case UP_RIGHT -> throw new UnsupportedOperationException("Unimplemented case: " + currentDirection);
        };
    }

    private class PointDirection {
        Point point;
        Direction direction;
                
        public PointDirection(Point point, Direction direction) {
            this.point = point;
            this.direction = direction;
        }
    }


}
