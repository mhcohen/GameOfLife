package hgl.task.game;

import hgl.task.game.elements.Coordinate;
import hgl.task.game.elements.Height;
import hgl.task.game.elements.MatrixGameGridFactory;
import hgl.task.game.elements.Width;
import hgl.task.game.rules.StandardRulesFactory;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static hgl.task.game.utils.Assert2DArrayEquals.assertStateEquals;
import static java.util.Collections.emptySet;

class GameOfLifeTest {

    @Test
    void gameOfLifeTest_Initialisation() {
        GameOfLife gameOfLife = new GameOfLife(new MatrixGameGridFactory(), new StandardRulesFactory())
                .initialiseGameGrid(Height.of(1), Width.of(1))
                .initialiseStartingPopulation(Set.of(Coordinate.of(1, 1)));

        int[][] expected = {{1, 1}};

        assertStateEquals(expected, gameOfLife.getLiveCells());
    }

    @ParameterizedTest
    @MethodSource("oneTurnScenarios")
    void takeTurn_RunGameForASingleGeneration_GameStateUpdatesAccordingly(int[][] expected, Height height, Width width, Set<Coordinate> coordinates) {
        GameOfLife gameOfLife = new GameOfLife(new MatrixGameGridFactory(), new StandardRulesFactory())
                .initialiseGameGrid(height, width)
                .initialiseStartingPopulation(coordinates);

        int[][] result = gameOfLife.takeTurn();

        assertStateEquals(expected, result);
    }

    public static Stream<Arguments> oneTurnScenarios() {
        return Stream.of(
                Arguments.of(Named.of("Start with no live cells, end with no live cells", new int[0][]), Height.of(200), Width.of(200), emptySet())
        );
    }
}