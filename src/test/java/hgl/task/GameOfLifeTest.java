package hgl.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    private GameOfLife gameOfLife;

    @Test
    void initialise_whenStartedWithValuesH0W0_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GameOfLife(0, 0));
    }

}