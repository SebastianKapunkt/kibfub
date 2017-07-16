package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.NUMBER;
import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;
import static de.htw.sebastiankapunkt.kipfub.model.ScaledField.maxScore;

public class HeatMapViewTest {

    @Test
    public void collectHeatMapTest() {
        ViewController view = new ViewController();
        ScaledField[][] field = new ScaledField[NUMBER][NUMBER];
        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                field[x][y] = new ScaledField(x * SCALE, y * SCALE, true);
                field[x][y].score = Math.random() * maxScore;
            }
        }

        field[32][32].score = 0;
        field[33][32].score = 0;
        field[34][32].score = 0;
        field[35][32].score = 0;

        field[32][33].score = 0;
        field[33][33].score = 0;
        field[34][33].score = 0;
        field[35][33].score = 0;

        field[32][34].score = 6;
        field[33][34].score = 6;
        field[34][34].score = 0;
        field[35][34].score = 0;

        field[32][35].score = 0;
        field[33][35].score = 0;
        field[34][35].score = 0;
        field[35][35].score = 0;

        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                view.drawScaledField(field[x][y]);
            }
        }

        Map<Node, Double> sum = new HashMap<>();

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                sum.put(new Node(x, y), sumOfRange(x, y, field));
            }
        }

        view.drawSum(sum);

        while (true) {

        }
    }

    private Double sumOfRange(int fromX, int fromY, ScaledField[][] field) {
        double sum = 0;

        for (int x = fromX * 4; x < 4 * (fromX + 1); x++) {
            for (int y = fromY * 4; y < 4 * (fromY + 1); y++) {
                if (field[x][y].isWalkable) {
                    sum += field[x][y].getScore();
                }
            }
        }

        return sum;
    }
}
