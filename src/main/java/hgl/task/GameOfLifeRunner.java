package hgl.task;

import hgl.task.game.CoordinateConverter;
import hgl.task.game.GameOfLife;
import hgl.task.game.elements.Coordinate;
import hgl.task.game.elements.Height;
import hgl.task.game.elements.MatrixGameGridFactory;
import hgl.task.game.elements.Width;
import hgl.task.game.output.ConsoleOutputterFactory;
import hgl.task.game.rules.StandardRulesFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Set;


@Command(name = "GameOfLife", version = "GameOfLife 1.0", mixinStandardHelpOptions = true)
public class GameOfLifeRunner implements Runnable {

    @Option(names = {"-h", "--height"}, description = "Height of the world in Cells. Defaults to 200")
    int height = 200;

    @Option(names = {"-w", "--width"}, description = "Width of the world in Cells. Defaults to 200")
    int width = 200;

    @Option(names = {"-g", "--generation"}, description = "Number of generations to run the game. Defaults to 100")
    int generations = 100;

    @Option(
            names = {"-p", "--population"},
            description = "List of initial population coordinates in x,y format (e.g., 1,1 1,2 2,1)",
            split = " ",
            converter = CoordinateConverter.class,
            arity = "1..*"
    )
    Set<Coordinate> initialPopulation = Set.of();


    @Override
    public void run() {
        GameOfLife gameOfLife = new GameOfLife(new MatrixGameGridFactory(), new StandardRulesFactory(), new ConsoleOutputterFactory())
                .initialiseGameGrid(Height.of(height), Width.of(width))
                .initialiseStartingPopulation(initialPopulation);

        gameOfLife.runGame(generations);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new GameOfLifeRunner()).execute(args);
        System.exit(exitCode);
    }
}