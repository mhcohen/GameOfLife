package hgl.task.game.elements;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class MatrixGameGrid implements GameGrid {

    private final int height;
    private final int width;
    private final Cell[][] grid;

    public MatrixGameGrid(Height height, Width width) {
        if (height.value() <= 0 || width.value() <= 0) {
            throw new IllegalArgumentException("height and width should be non-negative");
        }
        this.height = height.value();
        this.width = width.value();
        grid = new Cell[height.value()][width.value()];
        initialiseGrid();
    }

    private void initialiseGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Cell(Coordinate.of(x + 1, y + 1));
            }
        }
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
        return this.stream()
                .filter(Cell::isAlive)
                .map(Cell::getCoordinate)
                .map(Coordinate::toIntArray)
                .toArray(int[][]::new);
    }

    @Override
    public void flip(Coordinate coordinate) {
        validateCoordinate(coordinate);

        getCellAt(coordinate).transitionState();
    }

    @Override
    public List<Cell> getNeighbourhood(Coordinate coordinate) {
        return coordinateToNeighbourhood(coordinate)
                .map(this::getCellAt)
                .toList();
    }

    private Stream<Coordinate> coordinateToNeighbourhood(Coordinate coordinate) {
        return coordinate.getNeighbours().stream()
                .filter(this::isValidCoordinate);
    }

    private Cell getCellAt(Coordinate coordinate) {
        return grid[coordinate.getYZeroIndexed()][coordinate.getXZeroIndexed()];
    }

    private void validateCoordinate(Coordinate coordinate) {
        if (!isValidCoordinate(coordinate))
            throw new IllegalArgumentException("Coordinates must not be greater than width or height");
    }

    private boolean isValidCoordinate(Coordinate coordinate) {
        return coordinate.getXZeroIndexed() >= 0 && coordinate.getXZeroIndexed() < width && coordinate.getYZeroIndexed() >= 0 && coordinate.getYZeroIndexed() < height;
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
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                sb.append("%s".formatted(grid[y][x]));
//            }
//            sb.append(System.lineSeparator());
//        }
//        this.stream()
//                .forEach(cell -> {
//                    sb.append(cell.isAlive());
//                    if (cell.getCoordinate().y() == height)
//                        sb.append(System.lineSeparator());
//                });
        this.stream()
                .filter(Cell::isAlive)
                .map(Cell::getCoordinate)
                .map(Coordinate::toIntArray)
                .forEach(intArray -> sb.append(Arrays.toString(intArray)));
        return sb.toString();
    }

    @Override
    public Iterator<Cell> iterator() {
        return new GridIterator();
    }

    private class GridIterator implements Iterator<Cell> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < height * width;
        }

        @Override
        public Cell next() {
            int row = index / width;
            int col = index % width;
            index++;
            return grid[row][col];
        }
    }
}
