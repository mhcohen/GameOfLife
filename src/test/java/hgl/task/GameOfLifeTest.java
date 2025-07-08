package hgl.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    private GameOfLife gameOfLife;

    @BeforeEach
    void setUp() {
        gameOfLife = new GameOfLife();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "-1, 1",
            "1, -1"
    })
    void initialise_whenStartedWithInvalidHeightOrWidth_throwIllegalArgumentException(int height, int width) {
        assertThrows(IllegalArgumentException.class, () -> gameOfLife.initialise(height, width));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "200, 1",
            "1, 200",
            "1000, 1000"
    })
    void initialise_whenStartedWithValidHeight_createGridOfExpectedSize(int height, int width) {
        assertDoesNotThrow(() -> gameOfLife.initialise(height, width));
    }



}