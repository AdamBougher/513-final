package game;

import java.awt.Point;

public class boardCell {
    private cellState state;
    private Point position;

    public boardCell(cellState state, Point position) {
        this.state = state;
        this.position = position;
    }

    public cellState getState() {
        return state;
    }

    public Point getPosition() {
        return position;
    }
    
    public void setState(cellState state) {
        this.state = state;
    }

    public void setX(int x) {
        this.position.x = x;
    }

    public void setY(int y) {
        this.position.y = y;
    }

    @Override
    public String toString() {
        return """
                {
                    "state": "%s",
                    "x": %d,
                    "y": %d 
                }
                """.formatted(this.state, this.position.x, this.position.y);
    }

}
