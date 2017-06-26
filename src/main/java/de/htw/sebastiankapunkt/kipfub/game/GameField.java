package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Bot;
import de.htw.sebastiankapunkt.kipfub.model.RGBModel;
import de.htw.sebastiankapunkt.kipfub.representation.App;
import javafx.application.Application;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;


public class GameField {

    public static final int X_DIMENSION = 1024;
    public static final int Y_DIMENSION = 1024;
    private RGBModel[][] field;
    private Bot[][] bots = new Bot[3][3];
    private App app;

    public GameField() {
        field = new RGBModel[X_DIMENSION][Y_DIMENSION];
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
        for (int x = 0; x < X_DIMENSION; x++) {
            for (int y = 0; y < Y_DIMENSION; y++) {
                field[x][y] = client.isWalkable(x, y) ? new RGBModel(255, 255, 255) : new RGBModel(0, 0, 0);
            }
        }

        new Thread(() -> Application.launch(App.class)).start();
        app = App.waitForStart();
        app.drawGame(field);
    }

    public void applyColorChange(ColorChange colorChange) {
        updateBots(colorChange);
        if (app != null) {
            app.applyChange(colorChange);
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
