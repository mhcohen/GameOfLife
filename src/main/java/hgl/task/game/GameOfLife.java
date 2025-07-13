package hgl.task.game;

import hgl.task.game.elements.*;
import hgl.task.game.output.NonOutputterFactory;
import hgl.task.game.output.Outputter;
import hgl.task.game.output.OutputterFactory;
import hgl.task.game.rules.Rules;
import hgl.task.game.rules.RulesFactory;
import org.apache.commons.collections4.SetUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameOfLife {

    private final GameGridFactory gameGridFactory;
    private final Rules rules;
    private final Outputter outputter;
    private GameGrid gameGrid = null;
    private boolean populated = false;

    public GameOfLife(GameGridFactory gameGridFactory, RulesFactory rulesFactory) {
        this(gameGridFactory, rulesFactory, new NonOutputterFactory());
    }

    public GameOfLife(GameGridFactory gameGridFactory, RulesFactory rulesFactory, OutputterFactory outputterFactory) {
        this.gameGridFactory = gameGridFactory;
        this.rules = rulesFactory.getGameRules();
        this.outputter = outputterFactory.getOutputter();
    }

    public GameOfLife initialiseGameGrid(Height height, Width width) {
        if (gameGrid == null) {
            gameGrid = gameGridFactory.getGameGrid(height, width);
        }
        return this;
    }

    public GameOfLife initialiseStartingPopulation(Set<Coordinate> initialLiveCoordinates) {
        if (gameGrid == null) throw new IllegalStateException("GameGrid has not been initialised");
        if (populated) throw new IllegalStateException("GameGrid has already been populated");

        initialLiveCoordinates.forEach(gameGrid::flip);
        populated = true;
        return this;
    }

    public int[][] getLiveCells() {
        return gameGrid.getState();
    }

    public List<int[][]> runGame(int generationsToRun) {
        if (gameGrid == null) throw new IllegalStateException("GameGrid has not been initialised");
        if (!populated) throw new IllegalStateException("GameGrid has not been populated");

        if (generationsToRun < 1) throw new IllegalArgumentException("game must run for at least 1 turn");

        List<int[][]> results = new ArrayList<>();

        int i = 0;
        while (i < generationsToRun) {
            int[][] result = takeTurn();
            results.add(result);
            outputter.output(result);
            if (result.length == 0) break; // extinction
            i++;
        }

        return results;
    }

    private int[][] takeTurn() {
        Set<Cell> alive = getAllCellsThatAreAlive();
        Set<Cell> couldBeAlive = getAllCellsThatCouldBeAlive(alive);
        Set<Cell> shouldBeAlive = computeCellsThatShouldBeAlive(couldBeAlive);

        Set<Cell> shouldDie = SetUtils.difference(alive, shouldBeAlive);
        Set<Cell> shouldBirth = SetUtils.difference(shouldBeAlive, alive);

        Stream.of(shouldDie, shouldBirth)
                .flatMap(Collection::stream)
                .map(Cell::getCoordinate)
                .forEach(gameGrid::flip);

        return getLiveCells();
    }

    private Set<Cell> getAllCellsThatAreAlive() {
        return gameGrid.stream()
                .filter(Cell::isAlive)
                .collect(Collectors.toSet());
    }

    private Set<Cell> getAllCellsThatCouldBeAlive(Set<Cell> alive) {
        return alive.stream()
                .flatMap(cell -> Stream.concat(
                        Stream.of(cell),
                        gameGrid.getNeighbourhood(cell.getCoordinate()).stream()
                ))
                .collect(Collectors.toSet());
    }

    private Set<Cell> computeCellsThatShouldBeAlive(Set<Cell> couldBeAlive) {
        return couldBeAlive.stream()
                .filter(cell -> rules.shouldLive(cell, gameGrid.getNeighbourhood(cell.getCoordinate())))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "GameOfLife{" +
                "gameGrid=" + gameGrid +
                '}';
    }
}
