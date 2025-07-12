package hgl.task.game.elements;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateTest {

    @ParameterizedTest
    @MethodSource("neighbourScenarios")
    void getNeighbours_(Coordinate coordinate, List<Coordinate> expected) {
        assertEquals(expected, coordinate.getNeighbours());
    }

    public static Stream<Arguments> neighbourScenarios() {
        return Stream.of(
                Arguments.of(Coordinate.of(1, 1), List.of(Coordinate.of(0, 0), Coordinate.of(0, 1), Coordinate.of(0, 2), Coordinate.of(1, 0), Coordinate.of(1, 2), Coordinate.of(2, 0), Coordinate.of(2, 1), Coordinate.of(2, 2))),
                Arguments.of(Coordinate.of(0, 1), List.of(Coordinate.of(-1, 0), Coordinate.of(-1, 1), Coordinate.of(-1, 2), Coordinate.of(0, 0), Coordinate.of(0, 2), Coordinate.of(1, 0), Coordinate.of(1, 1), Coordinate.of(1, 2))),
                Arguments.of(Coordinate.of(1, 0), List.of(Coordinate.of(0, -1), Coordinate.of(0, 0), Coordinate.of(0, 1), Coordinate.of(1, -1), Coordinate.of(1, 1), Coordinate.of(2, -1), Coordinate.of(2, 0), Coordinate.of(2, 1)))
        );
    }
}