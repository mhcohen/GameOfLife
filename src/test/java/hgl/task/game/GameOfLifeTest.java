package hgl.task.game;

import hgl.task.game.elements.Coordinate;
import hgl.task.game.elements.Height;
import hgl.task.game.elements.MatrixGameGridFactory;
import hgl.task.game.elements.Width;
import hgl.task.game.output.ConsoleOutputterFactory;
import hgl.task.game.rules.StandardRulesFactory;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

import static hgl.task.game.utils.Assert2DArrayEquals.assertStateEquals;

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
    void runGame_RunGameForASingleGeneration_GameStateUpdatesAccordingly(int[][] expected, Height height, Width width, Set<Coordinate> coordinates) {
        GameOfLife gameOfLife = new GameOfLife(new MatrixGameGridFactory(), new StandardRulesFactory())
                .initialiseGameGrid(height, width)
                .initialiseStartingPopulation(coordinates);

        int[][] result = gameOfLife.runGame(1).getFirst();

        // TODO Criminal activity. I have the elements I need, but not the sort order, so cheating. Needs clarification.
        Arrays.sort(result, Comparator.comparing(a -> a[0]));
        Arrays.sort(expected, Comparator.comparing(a -> a[0]));

        assertStateEquals(expected, result);
    }

    public static Stream<Arguments> oneTurnScenarios() {
        return Stream.of(
                Arguments.of(Named.of("Start with no live cells, end with no live cells", new int[0][]), Height.of(200), Width.of(200), Collections.emptySet()),
                Arguments.of(Named.of("iggy", new int[][]{{5, 5}, {6, 4}, {7, 5}, {5, 6}, {6, 7}, {7, 6}}), Height.of(200), Width.of(200), Set.of(
                        Coordinate.of(5, 5),
                        Coordinate.of(6, 5),
                        Coordinate.of(7, 5),
                        Coordinate.of(5, 6),
                        Coordinate.of(6, 6),
                        Coordinate.of(7, 6))),
                Arguments.of(Named.of("stable", new int[][]{{5, 5}, {6, 4}, {7, 5}, {5, 6}, {6, 7}, {7, 6}}), Height.of(200), Width.of(200), Set.of(
                        Coordinate.of(5, 5),
                        Coordinate.of(6, 4),
                        Coordinate.of(7, 5),
                        Coordinate.of(5, 6),
                        Coordinate.of(6, 7),
                        Coordinate.of(7, 6))),
                Arguments.of(Named.of("Start with three live cells all touching, adjacent to a common dead cell, which ends up alive", new int[][]{{1, 1}, {2, 1}, {1, 2}, {2, 2}}), Height.of(200), Width.of(200), Set.of(Coordinate.of(1, 1), Coordinate.of(1, 2), Coordinate.of(2, 1)))
        );
    }

    @Test
    void runGame_GosperGliderGun() {
        GameOfLife gameOfLife = new GameOfLife(new MatrixGameGridFactory(), new StandardRulesFactory(), new ConsoleOutputterFactory())
                .initialiseGameGrid(Height.of(200), Width.of(200))
                .initialiseStartingPopulation(
                        Set.of(
                                Coordinate.of(2, 6),
                                Coordinate.of(2, 7),
                                Coordinate.of(3, 6),
                                Coordinate.of(3, 7),
                                Coordinate.of(12, 6),
                                Coordinate.of(12, 7),
                                Coordinate.of(12, 8),
                                Coordinate.of(13, 5),
                                Coordinate.of(13, 9),
                                Coordinate.of(14, 4),
                                Coordinate.of(14, 10),
                                Coordinate.of(15, 4),
                                Coordinate.of(15, 10),
                                Coordinate.of(16, 7),
                                Coordinate.of(17, 5),
                                Coordinate.of(17, 9),
                                Coordinate.of(18, 6),
                                Coordinate.of(18, 7),
                                Coordinate.of(18, 8),
                                Coordinate.of(19, 7),
                                Coordinate.of(22, 4),
                                Coordinate.of(22, 5),
                                Coordinate.of(22, 6),
                                Coordinate.of(23, 4),
                                Coordinate.of(23, 5),
                                Coordinate.of(23, 6),
                                Coordinate.of(24, 3),
                                Coordinate.of(24, 7),
                                Coordinate.of(26, 2),
                                Coordinate.of(26, 3),
                                Coordinate.of(26, 7),
                                Coordinate.of(26, 8),
                                Coordinate.of(36, 4),
                                Coordinate.of(36, 5),
                                Coordinate.of(37, 4),
                                Coordinate.of(37, 5)
                        )
                );

        gameOfLife.runGame(100);
    }
}