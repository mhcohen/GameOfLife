package hgl.task.game;

import hgl.task.game.elements.*;
import hgl.task.game.rules.Rules;
import hgl.task.game.rules.RulesFactory;

import java.util.Set;

public class GameOfLife {

    private final GameGridFactory gameGridFactory;
    private final Rules rules;
    private GameGrid gameGrid = null;
    private boolean populated = false;

    public GameOfLife(GameGridFactory gameGridFactory, RulesFactory rulesFactory) {
        this.gameGridFactory = gameGridFactory;
        this.rules = rulesFactory.getGameRules();
    }

    public GameOfLife(GameGridFactory gameGridFactory, RulesFactory rulesFactory, Height height, Width width, Set<Coordinate> initialLiveCoordinates) {
        this(gameGridFactory, rulesFactory);
        initialiseGameGrid(height, width);
        initialiseStartingPopulation(initialLiveCoordinates);
    }

    public GameOfLife initialiseGameGrid(Height height, Width width) {
        if (gameGrid == null) {
            gameGrid = gameGridFactory.getGameGrid(height, width);
        }
        return this;
    }

    public GameOfLife initialiseStartingPopulation(Set<Coordinate> initialLiveCoordinates) {
        if (gameGrid == null) {
            throw new IllegalStateException("GameGrid has not been initialised");
        }
        if (populated) {
            throw new IllegalStateException("GameGrid has already been initialised");
        }
        initialLiveCoordinates.forEach(gameGrid::flip);
        populated = true;
        return this;
    }

    public int[][] getLiveCells() {
        return gameGrid.getState();
    }

    public int[][] takeTurn() {
        return getLiveCells();
    }

    @Override
    public String toString() {
        return "GameOfLife{" +
                "gameGrid=" + gameGrid +
                '}';
    }
}
