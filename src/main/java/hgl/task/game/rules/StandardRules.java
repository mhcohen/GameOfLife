package hgl.task.game.rules;

import hgl.task.game.elements.Cell;

import java.util.List;

public class StandardRules implements Rules {

    @Override
    public boolean shouldLive(Cell cell, List<Cell> neighbours) {
        int sum = neighbours.stream()
                .map(Cell::isAlive)
                .mapToInt(StandardRules::booleanToInt)
                .sum();

        return shouldLive(cell.isAlive(), sum);
    }

    private static int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /*
    Any live cell with fewer than two live neighbours dies in the next generation as if caused by underpopulation.
    Any live cell with two or three live neighbours lives on to the next generation.
    Any live cell with more than three live neighbours dies in the next generation, as if by overpopulation.
    Any dead cell with exactly three live neighbours becomes a live cell in the next generation, as if by reproduction.
     */
    private boolean shouldLive(Boolean isAlive, int stateValue) {
        return switch (stateValue) {
            case 0, 1, 4, 5, 6, 7, 8 -> false;
            case 2 -> isAlive;
            case 3 -> true;
            default -> throw new IllegalStateException("Invalid state: " + stateValue);
        };
    }
}
