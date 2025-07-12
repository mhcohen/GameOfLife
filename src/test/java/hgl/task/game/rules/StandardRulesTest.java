package hgl.task.game.rules;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardRulesTest {

    StandardRules standardRules = new StandardRules();

    @ParameterizedTest
    @MethodSource("shouldLiveScenarios")
    void shouldLive(boolean expected, boolean startState, List<Boolean> neighbours) {
        boolean actual = standardRules.shouldLive(startState, neighbours);

        assertEquals(expected, actual);
    }

    public static Stream<Arguments> shouldLiveScenarios() {
        return Stream.of(
                Arguments.of(Named.of("Dead cell with 0 live neighbours stays dead", false), false, Collections.emptyList()),
                Arguments.of(Named.of("Live cell with 0 live neighbours dies", false), true, Collections.emptyList()),
                Arguments.of(Named.of("Dead cell with 3 live neighbours lives", true), false, List.of(true, true, true)),
                Arguments.of(Named.of("Dead cell with 2 live neighbours stays dead", false), false, List.of(true, true)),
                Arguments.of(Named.of("Dead cell with 4 live neighbours stays dead", false), false, List.of(true, true, true, true)),
                Arguments.of(Named.of("Live cell with 1 live neighbours dies", false), true, List.of(true)),
                Arguments.of(Named.of("Live cell with 2 live neighbours stays alive", true), true, List.of(true, true)),
                Arguments.of(Named.of("Live cell with 3 live neighbours stays alive", true), true, List.of(true, true, true)),
                Arguments.of(Named.of("Live cell with 4 live neighbours dies", false), true, List.of(true, true, true, true))
        );
    }
}