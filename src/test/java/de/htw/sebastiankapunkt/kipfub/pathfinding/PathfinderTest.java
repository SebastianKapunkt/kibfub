package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import org.junit.Test;

import java.util.LinkedList;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.NUMBER;
import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;

public class PathfinderTest {
    @Test
    public void aStar() throws Exception {
        ViewController view = new ViewController();
        ScaledField[][] field = new ScaledField[NUMBER][NUMBER];
        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                field[x][y] = new ScaledField(x * SCALE, y * SCALE, true);
            }
        }

        //wall right short
        for (int i = 10; i < 16; i++) {
            field[i][15] = new ScaledField(i * SCALE, 15 * SCALE, false);
        }

        //wall down
        for (int i = 16; i < 23; i++) {
            field[15][i] = new ScaledField(15 * SCALE, i * SCALE, false);
        }

        //wall down
        for (int i = 16; i < 23; i++) {
            field[23][i] = new ScaledField(23 * SCALE, i * SCALE, false);
        }

        //wall down
        for (int i = 23; i < 30; i++) {
            field[23][i] = new ScaledField(23 * SCALE, i * SCALE, false);
        }

        //wall right long
        for (int i = 0; i < 30; i++) {
            field[i][22] = new ScaledField(i * SCALE, 22 * SCALE, false);
        }

        Pathfinder pathfinder = new Pathfinder(field);
        Node start = new Node(14, 21);
        Node goal = new Node(0, 23);

        LinkedList<Node> result = pathfinder.aStar(start, goal);

        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                view.drawScaledField(field[x][y]);
            }
        }

        view.drawPath(result);
        while (true) {
            //
        }
    }

}