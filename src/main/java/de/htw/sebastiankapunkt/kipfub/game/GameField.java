package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Bot;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.representation.App;
import javafx.application.Application;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;


public class GameField {

    public static final int SCALED = 16;
    private ScaledField[][] scaledFields = new ScaledField[1024 / SCALED][1024 / SCALED];
    private Bot[][] bots = new Bot[3][3];
    private App app;
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

    public void fillInitialField(NetworkClient client) {

        if (myPlayerNumber == 1) {
            new Thread(() -> Application.launch(App.class)).start();
            app = App.waitForStart();
            app.drawGrid();
        }

        for (int x = 0; x < 1024 / SCALED; x++) {
            for (int y = 0; y < 1024 / SCALED; y++) {
                scaledFields[x][y] = fillScaledField(x * SCALED, y * SCALED, client);
                if (myPlayerNumber == 1) {
                    app.drawScaledField(scaledFields[x][y]);
                }
            }
        }
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

    public void applyColorChange(ColorChange colorChange) {
        updateBots(colorChange);

        ScaledField changes = scaledFields[colorChange.x / SCALED][colorChange.y / SCALED];

        if (colorChange.player == myPlayerNumber) {
            changes.addScore();
        } else {
            changes.removeScore();
        }
        if (myPlayerNumber == 1) {
            System.out.println(String.format("Score of cell (%s, %s) is %s", changes.fromX, changes.fromY, changes.getScore()));
            app.applyChange(changes);
        }
    }

    private void updateBots(ColorChange colorChange) {
        bots[colorChange.player][colorChange.bot].setX(colorChange.x);
        bots[colorChange.player][colorChange.bot].setY(colorChange.y);
    }

    public Bot[][] getBots() {
        return bots;
    }
}
