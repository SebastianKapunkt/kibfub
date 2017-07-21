package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;

import java.util.HashMap;
import java.util.Map;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;

public class HeatMapController {

    private ScaledField[][] game;
    public Map<Node, Double> layerOne = new HashMap<>();
    public Map<Node, Double> layerTwo = new HashMap<>();
    public static final int HEATMAP_MODIFIER = 4;

    public HeatMapController(ScaledField[][] game) {
        this.game = game.clone();
    }

    public void createHeatMap() {
        for (int x = 0; x < 1024 / SCALE / HEATMAP_MODIFIER; x++) {
            for (int y = 0; y < 1024 / SCALE / HEATMAP_MODIFIER; y++) {
                layerOne.put(new Node(x, y), sumForSmall(x, y, game));
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                layerTwo.put(new Node(x, y), sumForBig(x, y, layerOne));
            }
        }
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

    public Node getForLayerMax() {
        Map.Entry<Node, Double> one = null;
        for (Map.Entry<Node, Double> nodeDoubleEntry : layerOne.entrySet()) {
//            if (one == null || Math.abs(nodeDoubleEntry.getValue() - ScaledField.maxScore/2) < Math.abs(one.getValue() - ScaledField.maxScore/2)) {
            if (one == null || nodeDoubleEntry.getValue() < one.getValue()) {
                one = nodeDoubleEntry;
            }
        }

        return one.getKey();
    }

    public Node getSecondLayerMax(int type) {
        if (type == 1) {
            return getForLayerMax();
        }
        Map.Entry<Node, Double> one = null;
        Map.Entry<Node, Double> two = null;

        Map.Entry<Node, Double> first = null;
        Map.Entry<Node, Double> second = null;


        for (Map.Entry<Node, Double> nodeDoubleEntry : layerTwo.entrySet()) {
            if (one == null || one.getValue() > nodeDoubleEntry.getValue()) {
                two = one;
                one = nodeDoubleEntry;
            }
        }

        Map<Node, Double> nodesOfOne = fill(layerOne, one.getKey());
        Map<Node, Double> nodesOfTwo = fill(layerOne, two.getKey());

        for (Map.Entry<Node, Double> nodeDoubleEntry : nodesOfOne.entrySet()) {
            if (first == null || first.getValue() < nodeDoubleEntry.getValue()) {
                first = nodeDoubleEntry;
            }
        }

        for (Map.Entry<Node, Double> nodeDoubleEntry : nodesOfTwo.entrySet()) {
            if (second == null || Math.abs(nodeDoubleEntry.getValue() - ScaledField.maxScore/2) < Math.abs(second.getValue() - ScaledField.maxScore/2)) {
                second = nodeDoubleEntry;
            }
        }

        switch (type) {
            case 0:
                return second.getKey();
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
