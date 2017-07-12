package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Bot;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALED;


public class GameField {

    private ScaledField[][] scaledFields = new ScaledField[1024 / SCALED][1024 / SCALED];
    private Bot[][] bots = new Bot[3][3];
    private int myPlayerNumber;

    public GameField(int playerNumber) {
        myPlayerNumber = playerNumber;
        bots[0][0] = new Bot(0, 0);
        bots[0][1] = new Bot(1, 0);
        bots[0][2] = new Bot(2, 0);

        bots[1][0] = new Bot(0, 1);
        bots[1][1] = new Bot(1, 1);
        bots[1][2] = new Bot(2, 1);

        bots[2][0] = new Bot(0, 2);
        bots[2][1] = new Bot(1, 2);
        bots[2][2] = new Bot(2, 2);
    }

    public ScaledField createField(int x, int y, NetworkClient client) {
        ScaledField scaledField = fillScaledField(x * SCALED, y * SCALED, client);
        scaledFields[x][y] = scaledField;
        return scaledField;
    }

    private ScaledField fillScaledField(int xStart, int yStart, NetworkClient client) {
        for (int xZoomed = xStart; xZoomed < xStart + SCALED; xZoomed++) {
            for (int yZoomed = yStart; yZoomed < yStart + SCALED; yZoomed++) {
                if (!client.isWalkable(xZoomed, yZoomed)) {
                    return new ScaledField(xStart, yStart, false);
                }
            }
        }
        return new ScaledField(xStart, yStart, true);
    }

    public ScaledField applyColorChange(ColorChange colorChange) {
        updateBots(colorChange);

        ScaledField changes = getField(colorChange.x, colorChange.y);

        if (colorChange.player == myPlayerNumber) {
            changes.addScore();
        } else {
            changes.removeScore();
        }

        return changes;
    }

    private void updateBots(ColorChange colorChange) {
        bots[colorChange.player][colorChange.bot].setX(colorChange.x);
        bots[colorChange.player][colorChange.bot].setY(colorChange.y);
    }

    public Bot[][] getBots() {
        return bots;
    }

    private ScaledField getField(int x, int y) {
        return scaledFields[x / SCALED][y / SCALED];
    }
}
