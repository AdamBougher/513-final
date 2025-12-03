package game;

import java.awt.Point;

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

        Point nextPosition = getNextPosition(monsterPosition, currentDirection);
        cellCount++;

        return currentDirection;
        
    }

    private Point getNextPosition(Point currentPosition, Direction direction) {
        Point newPosition = new Point(currentPosition);
        switch (direction) {
            case UP -> newPosition.y -= 1;
            case DOWN -> newPosition.y += 1;
            case LEFT -> newPosition.x -= 1;
            case RIGHT -> newPosition.x += 1;
        }

        if(board.isValidPosition(newPosition)){
            return newPosition;
        }

        return getNextPosition(currentPosition, getNextDirection(currentDirection));
    }

    private Direction getNextDirection(Direction currentDirection) {
        return switch (currentDirection) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }


}
