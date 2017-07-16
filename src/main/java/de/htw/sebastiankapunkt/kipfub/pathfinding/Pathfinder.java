package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;

import java.util.*;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.NUMBER;

public class Pathfinder {

    private final ScaledField[][] scaledFields;
    private Set<Node> closeSet;
    private Set<Node> openSet;
    private Map<Node, Node> camefrom;
    private Map<Node, Integer> gScore;
    private Map<Node, Integer> fScore;
    private Node current;

    public Pathfinder(ScaledField[][] scaledFields) {
        this.scaledFields = scaledFields.clone();
        closeSet = new HashSet<>();
        camefrom = new HashMap<>();
        openSet = new HashSet<>();
        gScore = new HashMap<>();
        fScore = new HashMap<>();

        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                gScore.put(new Node(x, y), 1000000);
                fScore.put(new Node(x, y), 1000000);
            }
        }
    }

    public LinkedList<Node> aStar(Node start, Node goal) {
//        System.out.println(start.toString());
        System.out.println(goal.toString());
        openSet.add(start);
        gScore.put(start, 0);
        fScore.put(start, heuristicCostEstimate(start, goal));

        while (!openSet.isEmpty()) {
            current = getMinScore(openSet);
            if (current.equals(goal)) {
                return reconstructPath(camefrom, current);
            }

            openSet.remove(current);
            closeSet.add(current);

            for (Node neighbor : getNeighbors(current)) {
                if (closeSet.contains(neighbor)) {
                    continue;
                }
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }

                double tentative_gScore = gScore.get(current) + scaledFields[neighbor.x][neighbor.y].getScore();
                if (tentative_gScore >= gScore.get(neighbor)) {
                    continue;
                }

                camefrom.put(neighbor, current);
                gScore.put(neighbor, (int) tentative_gScore);
                fScore.put(neighbor, gScore.get(neighbor) + heuristicCostEstimate(neighbor, goal));
            }
        }

        return reconstructPath(camefrom, current);
    }

    private int heuristicCostEstimate(Node start, Node goal) {
        return (int) Math.sqrt(Math.pow(start.x - goal.x, 2) + Math.pow(start.y - goal.y, 2));
    }

    private List<Node> getNeighbors(Node current) {
        List<Node> neighbors = new ArrayList<>();
        //up
        if (current.y > 0) {
            addNode(neighbors, current.x, current.y - 1);
        }
        //right
        if (current.x + 1 < NUMBER) {
            addNode(neighbors, current.x + 1, current.y);
        }
        //down
        if (current.y + 1 < NUMBER) {
            addNode(neighbors, current.x, current.y + 1);
        }
        //left
        if (current.x > 0) {
            addNode(neighbors, current.x - 1, current.y);
        }
        //up left
        if (current.x > 0 && current.y > 0) {
            addNode(neighbors, current.x - 1, current.y - 1);
        }
        //up right
        if (current.x + 1 < NUMBER && current.y > 0) {
            addNode(neighbors, current.x + 1, current.y - 1);
        }
        //down right
        if (current.x + 1 < NUMBER && current.y + 1 < NUMBER) {
            addNode(neighbors, current.x + 1, current.y + 1);
        }
        //down left
        if (current.x > 0 && current.y + 1 < NUMBER) {
            addNode(neighbors, current.x - 1, current.y + 1);
        }
        return neighbors;
    }

    private void addNode(List<Node> neighbors, int x, int y) {
        if (scaledFields[x][y].isWalkable) {
            neighbors.add(new Node(x, y));
        }
    }

    private LinkedList<Node> reconstructPath(Map<Node, Node> camefrom, Node current) {
        LinkedList<Node> path = new LinkedList<>();
        path.add(current);
        while (camefrom.containsKey(current)) {
            current = camefrom.get(current);
            path.add(current);
        }
        return path;
    }

    private Node getMinScore(Set<Node> openSet) {
        Map<Node, Integer> scores = new HashMap<>();

        for (Node node : openSet) {
            scores.put(node, fScore.get(node));
        }

        Map.Entry<Node, Integer> min = null;
        for (Map.Entry<Node, Integer> entry : scores.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        assert min != null;
        return min.getKey();
    }
}
