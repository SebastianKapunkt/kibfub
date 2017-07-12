package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Bot;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.pathfinding.Node;
import de.htw.sebastiankapunkt.kipfub.pathfinding.Pathfinding;
import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

public class GameController {

    // 0 = rot, 1 = grün, 2 = blau
    private GameField game;
    private ViewController view;
    private NetworkClient client;
    public static final int SCALED = 16;
    private boolean isPathfindingRunning = false;

    public GameController(NetworkClient client) {
        this.client = client;

        view = new ViewController();
        game = new GameField(client.getMyPlayerNumber());
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

    public void startObserving() {
        Flowable.create((FlowableOnSubscribe<ColorChange>) e -> {
            ColorChange colorChange;
            while (client.isAlive()) {
                if ((colorChange = client.pullNextColorChange()) != null) {
                    e.onNext(colorChange);
                }
            }
            e.onComplete();
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(colorChange -> game.applyColorChange(colorChange))
                .map(scaledField -> view.applyColorChange(scaledField))
                .subscribeWith(new ResourceSubscriber<ScaledField>() {
                    @Override
                    public void onNext(ScaledField change) {
                        if (!isPathfindingRunning && game.getBots()[0][0].x != 0 && game.getBots()[0][2].x != 0) {
                            isPathfindingRunning = true;
                            Node start = new Node(game.getBots()[0][0].x / SCALED, game.getBots()[0][0].y / SCALED);
                            Node goal = new Node(game.getBots()[0][2].x / SCALED, game.getBots()[0][2].y / SCALED);
                            view.drawNode(start);
                            view.drawNode(goal);
                            Pathfinding pathfinding = new Pathfinding(game.getBoard());
                            view.drawPath(pathfinding.aStar(start, goal));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Game should be done");
                    }
                });
    }

    public Bot[][] getBots() {
        return game.getBots();
    }
}
