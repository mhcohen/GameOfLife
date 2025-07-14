package hgl.task.game;

import hgl.task.game.elements.Coordinate;
import picocli.CommandLine.ITypeConverter;

public class CoordinateConverter implements ITypeConverter<Coordinate> {

    @Override
    public Coordinate convert(String value) throws IllegalArgumentException {
        String[] parts = value.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format for coordinate: " + value + " (should be in x,y format)");
        }
        try {
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            return Coordinate.of(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Coordinates must be integers: " + value);
        }
    }
}
