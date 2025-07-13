package hgl.task.game.output;

public class NonOutputterFactory implements OutputterFactory {
    @Override
    public Outputter getOutputter() {
        return new NonOutputter();
    }
}
