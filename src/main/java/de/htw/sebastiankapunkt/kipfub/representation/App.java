package de.htw.sebastiankapunkt.kipfub.representation;

import de.htw.sebastiankapunkt.kipfub.model.RGBModel;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lenz.htw.kipifub.ColorChange;

import java.util.concurrent.CountDownLatch;

public class App extends Application {

    public static final CountDownLatch latch = new CountDownLatch(1);
    public static App self;
    private static int strokeWidth = 2;

    private GraphicsContext context;
    private int boardSize = 1024;

    public App() {
    }

    public static App waitForStart() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return self;
    }

    public static void setStartUp(App startUp) {
        self = startUp;
        latch.countDown();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game");
        Button button = new Button();
        button.setText("asd");

        StackPane layout = new StackPane();

        Canvas canvas = new Canvas(boardSize, boardSize);
        context = canvas.getGraphicsContext2D();
        layout.getChildren().add(canvas);


        Scene scene = new Scene(layout, boardSize, boardSize);
        primaryStage.setScene(scene);
        primaryStage.show();
        setStartUp(this);
    }

    public void drawGame(RGBModel[][] game) {
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                RGBModel current = game[x][y];
                context.setStroke(Color.rgb(current.getRed(), current.getGreen(), current.getBlue()));
                context.strokeLine(x, y, x, y);
            }
        }
    }

    public void applyChange(ColorChange colorChange) {
        switch (colorChange.player) {
            case 0:
                context.setFill(Color.RED);
                break;
            case 1:
                context.setFill(Color.GREEN);
                break;
            case 2:
                context.setFill(Color.BLUE);
                break;
        }

        context.fillOval(colorChange.x - 10, colorChange.y - 10, 20, 20);
    }

    public void drawGrid(int size) {
        context.setFill(Color.DARKCYAN);
        for (int x = size; x < boardSize; x = x + size) {
            context.fillRect(x, 0, strokeWidth, boardSize);
        }
        for (int y = size; y < boardSize; y = y + size) {
            context.fillRect(0, y, boardSize, strokeWidth);
        }
    }

    public void drawScaledField(ScaledField scaledField, int scale) {
        context.setFill(Color.BURLYWOOD);
        context.fillRect(
                scaledField.fromX + strokeWidth,
                scaledField.fromY + strokeWidth,
                scale - strokeWidth,
                scale - strokeWidth
        );
    }
}
