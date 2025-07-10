package hgl.task.game.elements;

public class MatrixGameGridFactory implements GameGridFactory {

    @Override
    public GameGrid getGameGrid(Height height, Width width) {
        return new MatrixGameGrid(height, width);
    }
}
