package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
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
    public static final int SCALED = 16;

    public GameController(NetworkClient client) {
        this.client = client;
//        this.playernumber = client.getMyPlayerNumber(); // 0 = rot, 1 = grün, 2 = blau

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
