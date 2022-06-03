package gui.graphics;

import data.Worker;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import workersOperations.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static gui.graphics.DrawingCoordinates.getCoordinates;

public class Graphics {
    private static GraphicsContext gc;
    private static long minSize = 1000000000;
    private static long max = 1;

    public static void draw(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        Collection<Worker> collection = new ArrayList<>(Storage.getInstance().get());
        long newMinSize = collection.stream().mapToLong(Worker::getValue).min().orElse(minSize) / 2;
        if (newMinSize < minSize) {
            minSize = newMinSize;
        }
        long maxAbsoluteX = collection.stream().mapToLong(w -> Math.abs(w.getCoordinates().getX())).max().orElse(max) + 1;
        long maxAbsoluteY = collection.stream().mapToLong(w -> Math.abs(w.getCoordinates().getY())).max().orElse(max) + 1;
        long newMax = Math.max(maxAbsoluteX, maxAbsoluteY);
        if (newMax > max) {
            max = newMax;
        }
        for (Worker w : Storage.getInstance().getNewElements()) {
            appendWorker(w, canvas, minSize, max);
        }
        if (!Storage.getInstance().getRemovedElements().isEmpty()) {
            for (Worker w : Storage.getInstance().getRemovedElements()) {
                eraseWorker(w, canvas, minSize);
                System.out.println("removed");
            }
            redraw(canvas, max, minSize);
        }
        if (!Storage.getInstance().getDifferedElements().isEmpty()) {
            for (Map.Entry<Worker, Worker> entry : Storage.getInstance().getDifferedElements().entrySet()) {
                updateWorker(entry.getKey(), entry.getValue(), canvas, minSize, max);
            }
        }
    }

    public static void drawAllNow(Canvas canvas) {
        for (Worker w : Storage.getInstance().get()) {
            drawWorker(w, canvas, minSize, max);
        }
    }

    private static void drawSample(double x, double y, double size, Paint p) {
        gc.setStroke(p);
        gc.setLineWidth(5 * size);
        gc.strokeLine(x, y - 30 * size, x, y + 30 * size);
        gc.strokeLine(x, y - 15 * size, x - 15 * size, y);
        gc.strokeLine(x, y - 15 * size, x + 15 * size, y);
        gc.strokeLine(x, y + 30 * size, x - 15 * size, y + 45 * size);
        gc.strokeLine(x, y + 30 * size, x + 15 * size, y + 45 * size);
        gc.strokeOval(x - 7.5 * size, y - 45 * size, 15 * size, 15 * size);
    }

    private static Paint getColor(String line) {
        int num = Math.abs(line.hashCode());
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(digits[num % 16]);
            num = num / 16;
        }
        return Paint.valueOf(builder.toString());
    }

    private static void eraseWorker(Worker worker, Canvas canvas, long minSize) {
        DrawingCoordinates coordinates = getCoordinates(worker, minSize, canvas);
        gc.clearRect(coordinates.x - 21, coordinates.y - 51, 50, 90);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void drawWorker(Worker worker, Canvas canvas, long minSize, long max) {
        drawWorkerWithColor(worker, canvas, max, getColor(worker.getCreator()));
    }

    private static void drawWorkerWithColor(Worker worker, Canvas canvas, long max, Paint paint) {
        double size = calculateSize(worker);
        DrawingCoordinates coordinates = getCoordinates(worker, max, canvas);
        drawSample(coordinates.x, coordinates.y, size, paint);
    }

    private static void appendWorker(Worker worker, Canvas canvas, long minSize, long max) {
        final double finalSize = calculateSize(worker);
        DrawingCoordinates coordinates = getCoordinates(worker, max, canvas);
        Paint paint = getColor(worker.getCreator());
        AnimationTimer animationTimer = new AnimationTimer() {
            double size = 0;

            @Override
            public void handle(long now) {
                gc.clearRect(coordinates.x - 20 * size, coordinates.y - 45 * size, 40 * size, 100 * size);
                size += 0.02 * finalSize;
                drawSample(coordinates.x, coordinates.y, size, paint);
                if (size > finalSize) {
                    stop();
                    redraw(canvas, max, minSize);
                }
            }
        };
        animationTimer.start();
    }

    private static void updateWorker(Worker old, Worker upd, Canvas canvas, long minSize, long max) {
        final double finalSize = 1 - Math.exp(1 - (double) upd.getValue() / minSize);
        DrawingCoordinates coordinates = getCoordinates(old, max, canvas);
        DrawingCoordinates finalCoordinates = getCoordinates(upd, max, canvas);
        Paint paint = getColor(upd.getCreator());
        AnimationTimer animationTimer = new AnimationTimer() {
            final double startSize = calculateSize(old);
            double size = startSize;
            double x = coordinates.x;
            double y = coordinates.y;
            int counter = 0;

            @Override
            public void handle(long now) {
                x += 0.01 * (finalCoordinates.x - coordinates.x);
                y += 0.01 * (finalCoordinates.y - coordinates.y);
                gc.clearRect(x - 30 * size, y - 60 * size, 55 * size, 125 * size);
                size += 0.01 * (finalSize - startSize);
                drawSample(x, y, size, paint);
                counter++;
                if (counter >= 100) {
                    stop();
                    redraw(canvas, max, minSize);
                }
            }
        };
        animationTimer.start();
    }

    private static void redraw(Canvas canvas, long max, long minSize) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Worker w : Storage.getInstance().get()) {
            drawWorker(w, canvas, minSize, max);
        }
    }

    private static double calculateSize(Worker w) {
        return 1 - Math.exp(1 - (double) w.getValue() / minSize);
    }

    public static long getMax() {
        return max;
    }
}
