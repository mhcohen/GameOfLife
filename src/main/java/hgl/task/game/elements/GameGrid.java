package hgl.task.game.elements;

public interface GameGrid {

    int height();
    int width();
    int[][] getState();

    void flip(int y, int x);
}
