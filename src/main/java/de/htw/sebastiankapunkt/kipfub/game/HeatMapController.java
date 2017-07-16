package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.representation.HeatMapView;

import java.util.HashMap;
import java.util.Map;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;

public class HeatMapController {

    private ScaledField[][] game;
    public Map<Node, Double> heatMap = new HashMap<>();
    public Map<Node, Double> arr = new HashMap<>();
    public static final int HEATMAP_MODIFIER = 4;
    private HeatMapView app;

    public HeatMapController(ScaledField[][] game) {
        this.game = game.clone();
    }

    public void createHeatMap() {
        for (int x = 0; x < 1024 / SCALE / HEATMAP_MODIFIER; x++) {
            for (int y = 0; y < 1024 / SCALE / HEATMAP_MODIFIER; y++) {
                heatMap.put(new Node(x, y), sumForSmall(x, y, game));
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                arr.put(new Node(x, y), sumForBig(x, y, heatMap));
            }
        }

//        new JFXPanel();
//        Platform.runLater(() -> {
//            try {
//                new HeatMapView().start(new Stage());
//                app = HeatMapView.waitForStart();
//                app.drawSum(heatMap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }

    private Double sumForSmall(int fromX, int fromY, ScaledField[][] field) {
        int count = 0;
        double sum = 0;

        for (int x = fromX * HEATMAP_MODIFIER; x < (HEATMAP_MODIFIER * (fromX + 1)) - 1; x++) {
            for (int y = fromY * HEATMAP_MODIFIER; y < (HEATMAP_MODIFIER * (fromY + 1)) - 1; y++) {
                if (field[x][y] != null && field[x][y].isWalkable) {
                    sum += field[x][y].getScore();
                    count++;
                }
            }
        }

        if (count > 0) {
            return sum / count;
        } else {
            return ScaledField.maxScore;
        }
    }

    private Double sumForBig(int fromX, int fromY, Map<Node, Double> field) {
        double sum = 0;
        int count = 0;

        for (int x = fromX * 2; x < 2 * (fromX + 1); x++) {
            for (int y = fromY * 2; y < 2 * (fromY + 1); y++) {
                sum += field.get(new Node(x, y));
                count++;
            }
        }

        if (count > 0) {
            return sum / count;
        } else {
            return ScaledField.maxScore;
        }
    }

    public Node getHighest(int type) {
        Map.Entry<Node, Double> one = null;
        Map.Entry<Node, Double> two = null;
        Map.Entry<Node, Double> three = null;

        Map.Entry<Node, Double> first = null;
        Map.Entry<Node, Double> second = null;
        Map.Entry<Node, Double> third = null;

        for (Map.Entry<Node, Double> nodeDoubleEntry : arr.entrySet()) {
            if (one == null || three == null || one.getValue() > nodeDoubleEntry.getValue()) {
                three = one;
                one = nodeDoubleEntry;
            }
        }
        for (Map.Entry<Node, Double> nodeDoubleEntry : arr.entrySet()) {
            if (two == null || Math.abs(nodeDoubleEntry.getValue() - 3) < Math.abs(two.getValue() - 3)) {
                two = nodeDoubleEntry;
            }
        }

        Map<Node, Double> nodesOfOne = fill(heatMap, one.getKey());
        Map<Node, Double> nodesOfTwo = fill(heatMap, two.getKey());
        Map<Node, Double> nodesOfThree = fill(heatMap, three.getKey());

        for (Map.Entry<Node, Double> nodeDoubleEntry : nodesOfOne.entrySet()) {
            if (first == null || first.getValue() < nodeDoubleEntry.getValue()) {
                first = nodeDoubleEntry;
            }
        }

        for (Map.Entry<Node, Double> nodeDoubleEntry : nodesOfTwo.entrySet()) {
            if (second == null || Math.abs(nodeDoubleEntry.getValue() - 3) < Math.abs(second.getValue() - 3)) {
                second = nodeDoubleEntry;
            }
        }

        for (Map.Entry<Node, Double> nodeDoubleEntry : nodesOfThree.entrySet()) {
            if (third == null || third.getValue() < nodeDoubleEntry.getValue()) {
                third = nodeDoubleEntry;
            }
        }

        switch (type) {
            case 0:
                return second.getKey();
            case 1:
                return third.getKey();
            case 2:
                return first.getKey();
            default:
                return first.getKey();
        }
    }

    private Map<Node, Double> fill(Map<Node, Double> heatMap, Node one) {
        Map<Node, Double> nodes = new HashMap<>();
        for (Map.Entry<Node, Double> node : heatMap.entrySet()) {
            if (one.x * 2 <= node.getKey().x && node.getKey().x < one.x * 2 + 2 &&
                    one.y * 2 <= node.getKey().y && node.getKey().y < one.y * 2 + 2) {
                nodes.put(node.getKey(), node.getValue());
            }
        }
        return nodes;
    }
}
