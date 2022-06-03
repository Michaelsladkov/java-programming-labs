package gui.graphics;

import data.Worker;
import javafx.scene.canvas.Canvas;

public class DrawingCoordinates {
    final double x;
    final double y;

    public DrawingCoordinates(double xCord, double yCord) {
        x = xCord;
        y = yCord;
    }

    public static DrawingCoordinates getCoordinates(Worker worker, long max, Canvas canvas) {
        double x;
        if (worker.getCoordinates().getX() != 0) {
            x = worker.getCoordinates().getX() * ((canvas.getWidth() * 0.75) / (2 * max));
        } else {
            x = 0;
        }
        double y;
        if (worker.getCoordinates().getY() != 0) {
            y = worker.getCoordinates().getY() * ((canvas.getHeight() * 0.75) / (2 * max));
        } else {
            y = 0;
        }
        x = x + canvas.getWidth() / 2;
        y = y + canvas.getHeight() / 2;
        return new DrawingCoordinates(x, y);
    }

    public static double getDistance(DrawingCoordinates c1, DrawingCoordinates c2) {
        return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.x - c2.x, 2));
    }
}
