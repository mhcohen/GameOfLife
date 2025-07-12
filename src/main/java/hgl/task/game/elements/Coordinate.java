package hgl.task.game.elements;

import java.util.Arrays;
import java.util.List;

/**
 * 2D coordinate.
 * The coordinate is assumed to be 1 indexed
 */
public record Coordinate(int x, int y) {

    private static final int[][] NEIGHBOUR_RELATIVE_COORDINATES = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public List<Coordinate> getNeighbours() {
        return Arrays.stream(NEIGHBOUR_RELATIVE_COORDINATES)
                .map(neighbour -> new Coordinate(x + neighbour[0], y + neighbour[1]))
                .toList();
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    public int getXZeroIndexed() {
        return x - 1;
    }

    public int getYZeroIndexed() {
        return y - 1;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
