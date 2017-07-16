package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.representation.HeatMapView;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;

public class HeatMapController {

    private ScaledField[][] game;
    public Map<Node, Double> heatMap = new HashMap<>();
    public static final int HEATMAP_MODIFIER = 4;
    private HeatMapView app;

    public HeatMapController(ScaledField[][] game) {
        this.game = game.clone();
    }

    public void createHeatMap() {
        for (int x = 0; x < SCALE; x++) {
            for (int y = 0; y < SCALE; y++) {
                heatMap.put(new Node(x, y), sumOfRange(x, y, game));
            }
        }

        new JFXPanel();
        Platform.runLater(() -> {
            try {
                new HeatMapView().start(new Stage());
                app = HeatMapView.waitForStart();
                app.drawSum(heatMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Double sumOfRange(int fromX, int fromY, ScaledField[][] field) {
        int count = 0;
        double sum = 0;

        for (int x = fromX * 4; x < 4 * (fromX + 1); x++) {
            for (int y = fromY * 4; y < 4 * (fromY + 1); y++) {
//                if (field[x][y] != null && field[x][y].isWalkable) {
                    sum += field[x][y].getScore();
                    count++;
//                }
            }
        }

        System.out.println(sum/count);
        return sum / count;
    }

    public Node getHighest() {
        Map.Entry<Node, Double> highest = null;

        for (Map.Entry<Node, Double> nodeDoubleEntry : heatMap.entrySet()) {
            if (highest == null || highest.getValue() > nodeDoubleEntry.getValue()) {
                highest = nodeDoubleEntry;
            }
        }

        return highest.getKey();
    }
}
