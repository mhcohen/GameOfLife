package hgl.task.game.elements;

public interface GameGrid {

    int height();

    int width();

    int[][] getState();

    void flip(Coordinate coordinate);

    boolean[] getNeighbourhood(Coordinate coordinate);
}
