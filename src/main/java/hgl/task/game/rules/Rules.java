package hgl.task.game.rules;

import java.util.List;

public interface Rules {

    boolean shouldLive(Boolean cell, List<Boolean> state);
}
