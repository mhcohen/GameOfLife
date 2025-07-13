package hgl.task.game.elements;

public enum CellState {
    ALIVE(1),
    DEAD(0);

    final int value;

    CellState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public CellState getInverse() {
        return ALIVE.equals(this) ? DEAD : ALIVE;
    }
}
