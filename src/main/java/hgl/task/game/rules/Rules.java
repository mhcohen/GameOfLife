package hgl.task.game.rules;

import hgl.task.game.elements.Cell;

import java.util.List;

public interface Rules {

    boolean shouldLive(Cell cell, List<Cell> state);
}
