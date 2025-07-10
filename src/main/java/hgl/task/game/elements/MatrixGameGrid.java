package hgl.task.game.elements;

import java.util.ArrayList;
import java.util.List;

public class MatrixGameGrid implements GameGrid {

    private final int height;
    private final int width;
    private final boolean[][] grid;

    public MatrixGameGrid(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new boolean[height][width];
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

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j]) {
                    state.add(new int[]{i+1, j+1});
                }
            }
        }

        return state.toArray(new int[state.size()][2]);
    }

    @Override
    public void flip(int y, int x) {
        if (!isValidY(y) || !isValidX(x)) {
            throw new IllegalArgumentException("y of %s should be in range 1...%s. x of %s should be in range of 1...%s.".formatted(y,x,width,height));
        }

        grid[y-1][x-1] = !grid[y-1][x-1];
    }

    private boolean isValidY(int y) {
        return y > 0 && y <= height;
    }

    private boolean isValidX(int x) {
        return x > 0 && x <= width;
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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append("%s".formatted(grid[i][j]));
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
