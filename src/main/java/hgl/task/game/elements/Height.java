package hgl.task.game.elements;

public record Height(int value) {

    public static Height of(int value) {
        return new Height(value);
    }
}
