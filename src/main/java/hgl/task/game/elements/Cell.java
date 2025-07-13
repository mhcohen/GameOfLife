package hgl.task.game.elements;

import java.util.Objects;

import static hgl.task.game.elements.CellState.ALIVE;
import static hgl.task.game.elements.CellState.DEAD;

public class Cell {
    private final Coordinate coordinate;
    private CellState state = DEAD;

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public CellState getState() {
        return state;
    }

    public boolean isAlive() {
        return ALIVE.equals(state);
    }

    public void transitionState() {
        this.state = state.getInverse();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell cell)) return false;
        return Objects.equals(coordinate, cell.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinate);
    }
}
