package hgl.task.game;

import hgl.task.game.elements.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static hgl.task.game.utils.Assert2DArrayEquals.assertStateEquals;
import static org.junit.jupiter.api.Assertions.*;

class GameGridTest {

    private GameGridFactory gameGridFactory;

    @BeforeEach
    void setUp() {
        gameGridFactory = new MatrixGameGridFactory();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "-1, 1",
            "1, -1"
    })
    void initialiseGameGrid_whenStartedWithInvalidHeightOrWidth_throwIllegalArgumentException(int height, int width) {
        assertThrows(IllegalArgumentException.class, () -> gameGridFactory.getGameGrid(Height.of(height), Width.of(width)));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "200, 1",
            "1, 200",
            "1000, 1000"
    })
    void initialiseGameGrid_whenStartedWithValidHeight_createGridOfExpectedSize(int height, int width) {
        GameGrid gameGrid = assertDoesNotThrow(() -> gameGridFactory.getGameGrid(Height.of(height), Width.of(width)));

        assertAll(
                () -> assertEquals(height, gameGrid.height()),
                () -> assertEquals(width, gameGrid.width())
        );
    }

    @Test
    void getState_whenNoActiveCellsInGrid_ReturnEmptyArray() {
        GameGrid gameGrid = gameGridFactory.getGameGrid(Height.of(1), Width.of(1));

        assertEquals(Collections.EMPTY_LIST, Arrays.asList(gameGrid.getState()));
    }

    @ParameterizedTest
    @MethodSource("invalidFlipScenarios")
    void flip_attemptingToFlipAStateThatIsOutOfBounds_ThrowIllegalArgumentException(Height height, Width width, Coordinate coordinate) {
        GameGrid gameGrid = gameGridFactory.getGameGrid(height, width);

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
        GameGrid gameGrid = gameGridFactory.getGameGrid(height, width);

        Arrays.asList(coordinates).forEach(gameGrid::flip);

        assertStateEquals(expected, gameGrid.getState());
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

    @ParameterizedTest
    @MethodSource("neighbourScenarios")
    void getNeighbourhood_WhenSuppliedAValidCoordinate_ReturnAllNeighboursStates(Boolean[] expected, Height height, Width width, Coordinate coordinate, Coordinate... coordinatesToFlip) {
        GameGrid gameGrid = gameGridFactory.getGameGrid(height, width);

        Arrays.asList(coordinatesToFlip).forEach(gameGrid::flip);

        Boolean[] result = gameGrid.getNeighbourhood(coordinate).stream()
                .map(Cell::isAlive)
                .toArray(Boolean[]::new);

        assertArrayEquals(expected, result);
    }

    public static Stream<Arguments> neighbourScenarios() {
        return Stream.of(
                Arguments.of(new Boolean[0], Height.of(1), Width.of(1), Coordinate.of(1, 1), new Coordinate[0]),
                Arguments.of(new Boolean[]{false}, Height.of(1), Width.of(2), Coordinate.of(1, 1), new Coordinate[0]),
                Arguments.of(new Boolean[]{false}, Height.of(1), Width.of(2), Coordinate.of(2, 1), new Coordinate[0]),
                Arguments.of(new Boolean[]{false}, Height.of(2), Width.of(1), Coordinate.of(1, 1), new Coordinate[0]),
                Arguments.of(new Boolean[]{false}, Height.of(2), Width.of(1), Coordinate.of(1, 2), new Coordinate[0]),
                Arguments.of(new Boolean[]{false, false, false}, Height.of(2), Width.of(2), Coordinate.of(1, 1), new Coordinate[0]),
                Arguments.of(new Boolean[]{false, false, false, false, false, false, false, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[0]),
                Arguments.of(new Boolean[]{false, false, false, false, false, false, false, false}, Height.of(200), Width.of(200), Coordinate.of(50, 100), new Coordinate[0]),
                Arguments.of(new Boolean[]{true, false, false, false, false, false, false, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(1, 1)}),
                Arguments.of(new Boolean[]{false, true, false, false, false, false, false, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(1, 2)}),
                Arguments.of(new Boolean[]{false, false, true, false, false, false, false, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(1, 3)}),
                Arguments.of(new Boolean[]{false, false, false, true, false, false, false, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(2, 1)}),
                Arguments.of(new Boolean[]{false, false, false, false, true, false, false, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(2, 3)}),
                Arguments.of(new Boolean[]{false, false, false, false, false, true, false, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(3, 1)}),
                Arguments.of(new Boolean[]{false, false, false, false, false, false, true, false}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(3, 2)}),
                Arguments.of(new Boolean[]{false, false, false, false, false, false, false, true}, Height.of(3), Width.of(3), Coordinate.of(2, 2), new Coordinate[]{new Coordinate(3, 3)})
        );
    }
}