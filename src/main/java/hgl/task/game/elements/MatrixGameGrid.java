package hgl.task.game.elements;

public record MatrixGameGrid(int height, int width) implements GameGrid {

    @Override
    public int[][] getState() {
        return new int[0][];
    }
}
