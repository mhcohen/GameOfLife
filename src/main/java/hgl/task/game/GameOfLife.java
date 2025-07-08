package hgl.task.game;

import hgl.task.game.elements.GameGrid;
import hgl.task.game.elements.GameGridFactory;

public class GameOfLife {

    private final GameGridFactory gameGridFactory;

    public GameOfLife(GameGridFactory gameGridFactory) {
        this.gameGridFactory = gameGridFactory;
    }

    public GameGrid initialiseGameGrid(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("height and width should be non-negative");
        }
        return gameGridFactory.getGameGrid(height, width);
    }

}
