package hgl.task.game.elements;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface GameGrid extends Iterable<Cell> {

    int height();

    int width();

    int[][] getState();

    void flip(Coordinate coordinate);

    List<Cell> getNeighbourhood(Coordinate coordinate);

    default Stream<Cell> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }
}
