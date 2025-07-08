package hgl.task.game.elements;

public class MatrixGameGridFactory implements GameGridFactory {

    @Override
    public GameGrid getGameGrid(int height, int width) {
        return new MatrixGameGrid(height, width);
    }
}
