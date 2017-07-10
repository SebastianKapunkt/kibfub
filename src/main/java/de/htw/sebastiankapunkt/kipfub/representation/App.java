package de.htw.sebastiankapunkt.kipfub.representation;

import de.htw.sebastiankapunkt.kipfub.game.GameField;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

import static de.htw.sebastiankapunkt.kipfub.model.ScaledField.maxScore;

public class App extends Application {

    public static final CountDownLatch latch = new CountDownLatch(1);
    public static App self;
    private static int strokeWidth = 1;

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

    public void applyChange(ScaledField scaledField) {
        int score = scaledField.getScore();

        try {

            context.setFill(new Color(score / maxScore, 0, 0, 1));

            context.fillRect(
                    scaledField.fromX + strokeWidth,
                    scaledField.fromY + strokeWidth,
                    GameField.SCALED - strokeWidth,
                    GameField.SCALED - strokeWidth
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawGrid() {
        context.setFill(Color.DARKCYAN);
        for (int x = GameField.SCALED; x < boardSize; x = x + GameField.SCALED) {
            context.fillRect(x, 0, strokeWidth, boardSize);
        }
        for (int y = GameField.SCALED; y < boardSize; y = y + GameField.SCALED) {
            context.fillRect(0, y, boardSize, strokeWidth);
        }
    }

    public void drawScaledField(ScaledField scaledField) {
        if (scaledField.isWalkable) {
            context.setFill(Color.WHITE);
        } else {
            context.setFill(Color.BLACK);
        }
        context.fillRect(
                scaledField.fromX + strokeWidth,
                scaledField.fromY + strokeWidth,
                GameField.SCALED - strokeWidth,
                GameField.SCALED - strokeWidth
        );
    }
}