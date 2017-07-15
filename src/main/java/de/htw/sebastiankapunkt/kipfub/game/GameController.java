package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.pathfinding.PathMover;
import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import lenz.htw.kipifub.net.NetworkClient;

public class GameController {

    public static final int SCALED = 16;

    // 0 = rot, 1 = grün, 2 = blau
    private GameField game;
    private ViewController view;
    private BrushController brush;
    private ColorChangeController colorChangeController;

    private PathMover mover;

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

        brush = new BrushController();
        brush.observe(colorChangeController.connect());

        mover = new PathMover();
        mover.observe(brush.connect());

        colorChangeController.observeColorChange(client);
    }

    public void initialize() {
        ScaledField field;
        for (int x = 0; x < 1024 / SCALED; x++) {
            for (int y = 0; y < 1024 / SCALED; y++) {
                field = game.createField(x, y, client);
                view.drawScaledField(field);
            }
        }
    }

//    public void startObserving() {
//        Flowable.create((FlowableOnSubscribe<ColorChange>) e -> {
//            ColorChange colorChange;
//            while (client.isAlive()) {
//                if ((colorChange = client.pullNextColorChange()) != null) {
//                    e.onNext(colorChange);
//                }
//            }
//            e.onComplete();
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .map(colorChange -> game.applyColorChange(colorChange))
//                .map(scaledField -> view.applyColorChange(scaledField))
//                .subscribeWith(new ResourceSubscriber<ScaledField>() {
//                    @Override
//                    public void onNext(ScaledField change) {
//                        if (!isPathfindingRunning && game.getBrushes()[0][0].x != 0 && game.getBrushes()[0][2].x != 0) {
//                            isPathfindingRunning = true;
//                            Node start = new Node(game.getBrushes()[0][0].x / SCALED, game.getBrushes()[0][0].y / SCALED);
//                            Node goal = new Node(game.getBrushes()[0][2].x / SCALED, game.getBrushes()[0][2].y / SCALED);
//                            view.drawNode(start);
//                            view.drawNode(goal);
//                            Pathfinding pathfinding = new Pathfinding(game.getBoard());
//                            LinkedList<Node> path = pathfinding.aStar(start, goal);
//                            view.drawPath(path);
//                            new PathMover(getBots()).doMoveFor(path);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        t.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println("Game should be done");
//                    }
//                });
//    }
}
