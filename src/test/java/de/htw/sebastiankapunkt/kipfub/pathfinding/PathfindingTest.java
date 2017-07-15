package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.Node;
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

        //wall right short
        for (int i = 10; i < 16; i++) {
            field[i][15] = new ScaledField(i * SCALED, 15 * SCALED, false);
        }

        //wall down
        for (int i = 16; i < 23; i++) {
            field[15][i] = new ScaledField(15 * SCALED, i * SCALED, false);
        }

        //wall down
        for (int i = 16; i < 23; i++) {
            field[23][i] = new ScaledField(23 * SCALED, i * SCALED, false);
        }

        //wall down
        for (int i = 23; i < 30; i++) {
            field[23][i] = new ScaledField(23 * SCALED, i * SCALED, false);
        }

        //wall right long
        for (int i = 0; i < 30; i++) {
            field[i][22] = new ScaledField(i * SCALED, 22 * SCALED, false);
        }

        Pathfinding pathfinding = new Pathfinding(field);
        Node start = new Node(14, 21);
        Node goal = new Node(0, 23);

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