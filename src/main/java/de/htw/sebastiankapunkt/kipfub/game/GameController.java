package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.pathfinding.PathMover;
import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import lenz.htw.kipifub.net.NetworkClient;

public class GameController {

    public static final int SCALE = 16;
    public static final int NUMBER = 1024 / SCALE;

    // 0 = rot, 1 = grün, 2 = blau
    private GameField game;
    private ViewController view;
    private BrushController brush;
    private ColorChangeController colorChangeController;

    private NetworkClient client;
    private int myPlayerNumber;

    public GameController(NetworkClient client) {
        this.client = client;

        myPlayerNumber = client.getMyPlayerNumber();
        colorChangeController = new ColorChangeController();

        game = new GameField(myPlayerNumber);
        game.observe(colorChangeController.connect());

        view = new ViewController();
        view.observe(game.connect());

        brush = new BrushController(myPlayerNumber);
        brush.observe(colorChangeController.connect());

        PathMover mover = new PathMover(brush.getMyBrushOne(), game, client);
        mover.observe(brush.connect());
//        view.observePath(mover.connect());

        PathMover mover2 = new PathMover(brush.getMyBrushTwo(), game, client);
        mover2.observe(brush.connect());
//        view.observePath(mover2.connect());

        PathMover mover3 = new PathMover(brush.getMyBrushThree(), game, client);
        mover3.observe(brush.connect());
//        view.observePath(mover3.connect());

        colorChangeController.observeColorChange(client);
    }

    public void initialize() throws InterruptedException {
        ScaledField field;
        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                field = game.createField(x, y, client);
                view.drawScaledField(field);
            }
        }
    }
}
