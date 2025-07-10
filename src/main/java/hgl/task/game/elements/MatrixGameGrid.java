package hgl.task.game.elements;

import java.util.ArrayList;
import java.util.List;

public class MatrixGameGrid implements GameGrid {

    private final int height;
    private final int width;
    private final boolean[][] grid;

    public MatrixGameGrid(Height height, Width width) {
        this.height = height.value();
        this.width = width.value();
        grid = new boolean[height.value()][width.value()];
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int[][] getState() {
        List<int[]> state = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x]) {
                    // Based on the tests ran, it seems the task expects us to traverse the grid by height first.
                    // For example. My test expected : [[5, 5], [6, 5], [7, 5], [5, 6], [6, 6], [7, 6]]
                    // If I traverse by width and then height, I get: [[5, 5], [5, 6], [5, 7], [6, 5], [6, 6], [6, 7]]
                    // Which are the correct values, but in the wrong order.
                    // Only when I traverse by height first, but still output [X, Y] do I match the expected orders.
                    state.add(new int[]{x + 1, y + 1});
                }
            }
        }

        return state.toArray(new int[state.size()][2]);
    }

    @Override
    public void flip(Coordinate coordinate) {
        validateCoordinate(coordinate);

        grid[coordinate.y()][coordinate.x()] = !grid[coordinate.y()][coordinate.x()];
    }

    private void validateCoordinate(Coordinate coordinate) {
        if (coordinate.x() >= width || coordinate.y() >= height)
            throw new IllegalArgumentException("Coordinates must not be greater than width or height");
    }

    @Override
    public String toString() {
        return "MatrixGameGrid{" +
                "height=" + height +
                ", width=" + width +
                ", grid=" + gridToString() +
                '}';
    }

    private String gridToString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append("%s".formatted(grid[y][x]));
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
