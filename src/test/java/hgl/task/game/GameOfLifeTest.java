package hgl.task.game;

import hgl.task.game.elements.GameGrid;
import hgl.task.game.elements.MatrixGameGridFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;

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
}