package de.htw.sebastiankapunkt.kipfub.representation;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;
import static de.htw.sebastiankapunkt.kipfub.game.HeatMapController.HEATMAP_MODIFIER;

public class HeatMapView extends Application {

    public static final CountDownLatch latch = new CountDownLatch(1);
    public static HeatMapView self;
    private static int strokeWidth = 1;

    private GraphicsContext context;
    private int boardSize = 1024;

    public HeatMapView() {
    }

    public static HeatMapView waitForStart() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return self;
    }

    public static void setStartUp(HeatMapView startUp) {
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

    public void drawSum(Map<Node, Double> sum) {
        for (Map.Entry<Node, Double> nodeIntegerEntry : sum.entrySet()) {
            context.setFill(new Color(1, 0, 0, 1 - nodeIntegerEntry.getValue() / 6));
            context.fillRect(
                    nodeIntegerEntry.getKey().x * SCALE * HEATMAP_MODIFIER,
                    nodeIntegerEntry.getKey().y * SCALE * HEATMAP_MODIFIER,
                    SCALE * HEATMAP_MODIFIER,
                    SCALE * HEATMAP_MODIFIER
            );
        }
    }
}
