package hgl.task.game.utils;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Assert2DArrayEquals {

    public static void assertStateEquals(int[][] expected, int[][] actual) {
        assertEquals(expected.length, actual.length, "array length mismatch");

        List<Executable> assertions = new ArrayList<>();

        for (int i = 0; i < expected.length; i++) {
            assertions.add(assertElementsAreEqual(expected[i], actual[i]));
        }

        assertAll(
                "%s != %s".formatted(Arrays.deepToString(expected), Arrays.deepToString(actual)),
                assertions.stream()
        );
    }

    private static Executable assertElementsAreEqual(int[] expected, int[] actual) {
        return () -> assertArrayEquals(expected, actual, "array contents mismatch %s != %s".formatted(Arrays.toString(expected), Arrays.toString(actual)));
    }
}
