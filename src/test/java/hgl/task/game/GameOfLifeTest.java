package hgl.task.game;

import hgl.task.game.elements.GameGrid;
import hgl.task.game.elements.MatrixGameGridFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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



}