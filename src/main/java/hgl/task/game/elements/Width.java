package hgl.task.game.elements;

public record Width(int value) {

    public static Width of(int value) {
        return new Width(value);
    }
}
