package hgl.task.game.output;

public class ConsoleOutputterFactory implements OutputterFactory {
    @Override
    public Outputter getOutputter() {
        return new ConsoleOutputter();
    }
}
