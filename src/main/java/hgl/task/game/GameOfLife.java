package hgl.task.game;

import hgl.task.game.elements.GameGrid;
import hgl.task.game.elements.GameGridFactory;
import hgl.task.game.elements.Height;
import hgl.task.game.elements.Width;

public class GameOfLife {

    private final GameGridFactory gameGridFactory;

    public GameOfLife(GameGridFactory gameGridFactory) {
        this.gameGridFactory = gameGridFactory;
    }

    public GameGrid initialiseGameGrid(Height height, Width width) {
        if (height.value() <= 0 || width.value() <= 0) {
            throw new IllegalArgumentException("height and width should be non-negative");
        }
        return gameGridFactory.getGameGrid(height, width);
    }

}
