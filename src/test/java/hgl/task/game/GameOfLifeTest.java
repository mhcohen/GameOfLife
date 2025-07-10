package hgl.task.game;

import hgl.task.game.elements.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    private GameOfLife gameOfLife;

    @BeforeEach
    void setUp() {
        gameOfLife = new GameOfLife(new MatrixGameGridFactory());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "-1, 1",
            "1, -1"
    })
    void initialiseGameGrid_whenStartedWithInvalidHeightOrWidth_throwIllegalArgumentException(int height, int width) {
        assertThrows(IllegalArgumentException.class, () -> gameOfLife.initialiseGameGrid(Height.of(height), Width.of(width)));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "200, 1",
            "1, 200",
            "1000, 1000"
    })
    void initialiseGameGrid_whenStartedWithValidHeight_createGridOfExpectedSize(int height, int width) {
        GameGrid gameGrid = assertDoesNotThrow(() -> gameOfLife.initialiseGameGrid(Height.of(height), Width.of(width)));

        assertAll(
                () -> assertEquals(height, gameGrid.height()),
                () -> assertEquals(width, gameGrid.width())
        );
    }

    @Test
    void getState_whenNoActiveCellsInGrid_ReturnEmptyArray() {
        GameGrid gameGrid = gameOfLife.initialiseGameGrid(Height.of(1), Width.of(1));

        assertEquals(Collections.EMPTY_LIST, Arrays.asList(gameGrid.getState()));
    }

    @ParameterizedTest
    @MethodSource("invalidFlipScenarios")
    void flip_attemptingToFlipAStateThatIsOutOfBounds_ThrowIllegalArgumentException(Height height, Width width, Coordinate coordinate) {
        GameGrid gameGrid = gameOfLife.initialiseGameGrid(height, width);

        assertThrows(IllegalArgumentException.class, () -> gameGrid.flip(coordinate));
    }

    private static Stream<Arguments> invalidFlipScenarios() {
        return Stream.of(
                Arguments.of(Height.of(1), Width.of(1), new Coordinate(1, 2)),
                Arguments.of(Height.of(1), Width.of(1), new Coordinate(2, 1)),
                Arguments.of(Height.of(10), Width.of(10), new Coordinate(11, 5)),
                Arguments.of(Height.of(10), Width.of(10), new Coordinate(5, 11))
        );
    }

    @ParameterizedTest
    @MethodSource("validFlipScenarios")
    void flip_flippingAValidCell_GameStateReflectsTheFlippedCell(int[][] expected, Height height, Width width, Coordinate... coordinates) {
        GameGrid gameGrid = gameOfLife.initialiseGameGrid(height, width);

        Arrays.asList(coordinates).forEach(gameGrid::flip);

        assertStatesAreEqual(expected, gameGrid.getState());
    }

    public static Stream<Arguments> validFlipScenarios() {
        return Stream.of(
                // No flips, final state of nothing flipped
                Arguments.of(new int[0][], Height.of(1), Width.of(1), new Coordinate[0]),
                Arguments.of(new int[][]{{1, 1}}, Height.of(1), Width.of(1),
                        new Coordinate[]{new Coordinate(1, 1)}
                ),
                Arguments.of(new int[][]{{5, 5}, {6, 5}, {7, 5}, {5, 6}, {6, 6}, {7, 6}},
                        Height.of(10),
                        Width.of(20),
                        new Coordinate[]{
                                Coordinate.of(5, 5),
                                Coordinate.of(6, 5),
                                Coordinate.of(7, 5),
                                Coordinate.of(5, 6),
                                Coordinate.of(6, 6),
                                Coordinate.of(7, 6)}
                ),
                // flip then unflip, final state of nothing flipped
                Arguments.of(new int[0][], Height.of(1), Width.of(1),
                        new Coordinate[]{Coordinate.of(1, 1), Coordinate.of(1, 1)}
                )
        );
    }

    public static void assertStatesAreEqual(int[][] expected, int[][] actual) {
        assertEquals(expected.length, actual.length, "array length mismatch");

        List<Executable> assertions = new ArrayList<>();

        for (int i = 0; i < expected.length; i++) {
            assertions.add(assertElementsAreEqual(expected[i], actual[i]));
        }

        assertAll(
                assertions.stream()
        );
    }

    private static Executable assertElementsAreEqual(int[] expected, int[] actual) {
        return () -> assertArrayEquals(expected, actual, "array contents mismatch");
    }
}