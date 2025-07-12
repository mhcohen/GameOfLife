package hgl.task.game;

import hgl.task.game.elements.GameGrid;
import hgl.task.game.elements.GameGridFactory;
import hgl.task.game.elements.Height;
import hgl.task.game.elements.Width;

public class GameOfLife {

    private final GameGridFactory gameGridFactory;
    private GameGrid gameGrid = null;

    public GameOfLife(GameGridFactory gameGridFactory) {
        this.gameGridFactory = gameGridFactory;
    }
    
    public GameOfLife(GameGridFactory gameGridFactory, Height height, Width width) {
        this(gameGridFactory);
        initialiseGameGrid(height, width);
    }

    public void initialiseGameGrid(Height height, Width width) {
        if (gameGrid == null) {
            gameGrid = gameGridFactory.getGameGrid(height, width);
        }
    }

}
