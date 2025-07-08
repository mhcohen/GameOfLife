package hgl.task;

public class GameOfLife {
    public GameOfLife() {

    }

    public void initialise(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("height and width should be non-negative");
        }
    }
}
