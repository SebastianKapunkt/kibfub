package de.htw.sebastiankapunkt.kipfub.representation;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.pathfinding.Node;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALED;
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
        double score = scaledField.getScore();

        try {
            context.setFill(new Color(score / maxScore, score / maxScore, score / maxScore, 1));

            context.fillRect(
                    scaledField.fromX + strokeWidth,
                    scaledField.fromY + strokeWidth,
                    SCALED - strokeWidth,
                    SCALED - strokeWidth
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawGrid() {
        context.setFill(Color.DARKCYAN);
        for (int x = SCALED; x < boardSize; x = x + SCALED) {
            context.fillRect(x, 0, strokeWidth, boardSize);
        }
        for (int y = SCALED; y < boardSize; y = y + SCALED) {
            context.fillRect(0, y, boardSize, strokeWidth);
        }
    }

    public void drawScaledField(ScaledField scaledField) {
        if (scaledField.isWalkable) {
            double score = scaledField.getScore();
            context.setFill(new Color(score / maxScore, score / maxScore, score / maxScore, 1));
        } else {
            context.setFill(Color.ANTIQUEWHITE);
        }
        context.fillRect(
                scaledField.fromX + strokeWidth,
                scaledField.fromY + strokeWidth,
                SCALED - strokeWidth,
                SCALED - strokeWidth
        );
    }

    public void drawPath(LinkedList<Node> nodes) {
        context.setFill(new Color(1, 0, 1, 0.5));
        for (Node node : nodes) {
            context.fillRect(
                    node.x * SCALED + strokeWidth,
                    node.y * SCALED + strokeWidth,
                    SCALED - strokeWidth,
                    SCALED - strokeWidth
            );
        }
    }

    public void drawNode(Node node) {
        context.setFill(new Color(0, 1, 0, 0.5));

        context.fillRect(
                node.x * SCALED + strokeWidth,
                node.y * SCALED + strokeWidth,
                SCALED - strokeWidth,
                SCALED - strokeWidth
        );
    }

    public void drawSum(Map<Node, Double> sum) {
        for (Map.Entry<Node, Double> nodeIntegerEntry : sum.entrySet()) {
            context.setFill(new Color(1, 0, 0, 1 - nodeIntegerEntry.getValue() / 96));
            context.fillRect(
                    nodeIntegerEntry.getKey().x * 64,
                    nodeIntegerEntry.getKey().y * 64,
                    64,
                    64
            );

        }
    }
}
