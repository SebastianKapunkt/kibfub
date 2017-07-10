package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

public class GameController {

    private GameField game;
    private ViewController view;
    private NetworkClient client;
    private int playernumber;

    public GameController(NetworkClient client) {
        this.client = client;
        this.playernumber = client.getMyPlayerNumber(); // 0 = rot, 1 = grün, 2 = blau

        view = new ViewController(playernumber);
        game = new GameField(playernumber);


        game.fillInitialField(client);
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
                .subscribeWith(new ResourceSubscriber<ColorChange>() {
                    @Override
                    public void onNext(ColorChange change) {
                        game.applyColorChange(change);
//                                view.applyColorChange(change);
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
}
