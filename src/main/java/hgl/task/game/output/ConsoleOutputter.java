package hgl.task.game.output;

import java.util.Arrays;

public class ConsoleOutputter implements Outputter {
    @Override
    public void output(int[][] toOutput) {
        System.out.println(Arrays.deepToString(toOutput));
    }
}
