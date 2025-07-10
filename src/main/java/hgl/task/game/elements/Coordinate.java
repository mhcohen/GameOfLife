package hgl.task.game.elements;

/**
 * 2D coordinate
 * @param x
 * @param y
 */
public record Coordinate(int x, int y) {

    public Coordinate(int x, int y) {
        if(x < 1 || y < 1) throw new IllegalArgumentException("Coordinates must not be negative");
        this.x = x-1;
        this.y = y-1;
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }
}
