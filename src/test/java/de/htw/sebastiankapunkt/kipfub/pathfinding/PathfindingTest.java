package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import org.junit.Test;

import java.util.LinkedList;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALED;

public class PathfindingTest {
    @Test
    public void aStar() throws Exception {
        ViewController view = new ViewController();
        ScaledField[][] field = new ScaledField[1024 / SCALED][1024 / SCALED];
        for (int x = 0; x < 1024 / SCALED; x++) {
            for (int y = 0; y < 1024 / SCALED; y++) {
                field[x][y] = new ScaledField(x * SCALED, y * SCALED, true);
            }
        }

        field[10][15] = new ScaledField(10 * SCALED, 15 * SCALED, false);
        field[11][15] = new ScaledField(11 * SCALED, 15 * SCALED, false);
        field[12][15] = new ScaledField(12 * SCALED, 15 * SCALED, false);
        field[13][15] = new ScaledField(13 * SCALED, 15 * SCALED, false);
        field[14][15] = new ScaledField(14 * SCALED, 15 * SCALED, false);
        field[15][15] = new ScaledField(15 * SCALED, 15 * SCALED, false);
        field[15][16] = new ScaledField(15 * SCALED, 16 * SCALED, false);
        field[15][17] = new ScaledField(15 * SCALED, 17 * SCALED, false);
        field[15][18] = new ScaledField(15 * SCALED, 18 * SCALED, false);
        field[15][19] = new ScaledField(15 * SCALED, 19 * SCALED, false);
        field[15][20] = new ScaledField(15 * SCALED, 20 * SCALED, false);
        field[15][21] = new ScaledField(15 * SCALED, 21 * SCALED, false);
        field[15][22] = new ScaledField(15 * SCALED, 22 * SCALED, false);
        field[14][22] = new ScaledField(14 * SCALED, 22 * SCALED, false);
        field[13][22] = new ScaledField(13 * SCALED, 22 * SCALED, false);
        field[12][22] = new ScaledField(12 * SCALED, 22 * SCALED, false);
        field[11][22] = new ScaledField(11 * SCALED, 22 * SCALED, false);
        field[10][22] = new ScaledField(10 * SCALED, 22 * SCALED, false);


        Pathfinding pathfinding = new Pathfinding(field);
        Node start = new Node(13, 19);
        Node goal = new Node(16, 19);

        LinkedList<Node> result = pathfinding.aStar(start, goal);

        for (int x = 0; x < 1024 / SCALED; x++) {
            for (int y = 0; y < 1024 / SCALED; y++) {
                view.drawScaledField(field[x][y]);
            }
        }

        view.drawPath(result);
        while (true) {
            //
        }
    }

}