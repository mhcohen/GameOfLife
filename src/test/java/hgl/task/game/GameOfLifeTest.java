package hgl.task.game;

import hgl.task.game.elements.GameGrid;
import hgl.task.game.elements.MatrixGameGridFactory;
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
        assertThrows(IllegalArgumentException.class, () -> gameOfLife.initialiseGameGrid(height, width));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "200, 1",
            "1, 200",
            "1000, 1000"
    })
    void initialiseGameGrid_whenStartedWithValidHeight_createGridOfExpectedSize(int height, int width) {
        GameGrid gameGrid = assertDoesNotThrow(() -> gameOfLife.initialiseGameGrid(height, width));

        assertAll(
                () -> assertEquals(height, gameGrid.height()),
                () -> assertEquals(width, gameGrid.width())
        );
    }

    @Test
    void getState_whenNoActiveCellsInGrid_ReturnEmptyArray() {
        GameGrid gameGrid = gameOfLife.initialiseGameGrid(1, 1);

        assertEquals(Collections.EMPTY_LIST, Arrays.asList(gameGrid.getState()));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1, 2",
            "1, 1, 2, 1",
            "1, 1, 0, 1",
            "1, 1, 1, 0",
            "1, 1, -1, 1",
            "1, 1, 1, -1",
            "10, 10, 11, 5",
            "10, 10, 5, 11",
    })
    void flip_attemptingToFlipAStateThatIsOutOfBounds_ThrowIllegalArgumentException(int height, int width, int y, int x) {
        GameGrid gameGrid = gameOfLife.initialiseGameGrid(height, width);

        assertThrows(IllegalArgumentException.class, () -> gameGrid.flip(y, x));
    }

    @ParameterizedTest
    @MethodSource("validFlipScenarios")
    void flip_flippingAValidCell_GameStateReflectsTheFlippedCell(int height, int width, int y, int x, int[][] expected) {
        GameGrid gameGrid = gameOfLife.initialiseGameGrid(height, width);

        gameGrid.flip(y, x);

        assertStatesAreEqual(expected, gameGrid.getState());
    }

    public static Stream<Arguments> validFlipScenarios() {
        return Stream.of(
                Arguments.of(new int[][]{{1, 1}}, 1, 1, 1, 1)
        );
    }

    public static void assertStatesAreEqual(int[][] expected, int[][] actual) {

        assertEquals(expected.length, actual.length, "array length mismatch");

        List<Executable> assertions = new ArrayList<>();

        for(int i = 0; i < expected.length; i++) {
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