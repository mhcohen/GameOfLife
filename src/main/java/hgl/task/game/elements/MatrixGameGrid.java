package hgl.task.game.elements;

public record MatrixGameGrid(int height, int width) implements GameGrid {

    @Override
    public int[][] getState() {
        return new int[0][];
    }

    @Override
    public void flip(int y, int x) {
        throw new IllegalArgumentException("y of %s should be in range 1...%s. x of %s should be in range of 1...%s.".formatted(y,x,width,height));
    }
}
