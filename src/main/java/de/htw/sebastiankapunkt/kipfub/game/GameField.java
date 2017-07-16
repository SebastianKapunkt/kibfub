package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.NUMBER;
import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;


public class GameField {

    private ScaledField[][] game = new ScaledField[NUMBER][NUMBER];

    private int myPlayerNumber;
    private PublishSubject<ScaledField> subject = PublishSubject.create();

    public GameField(int playerNumber) {
        myPlayerNumber = playerNumber;
    }

    public void observe(PublishSubject<ColorChange> connect) {
        connect.subscribe(new DisposableObserver<ColorChange>() {
            @Override
            public void onNext(ColorChange colorChange) {
                ScaledField changes = getField(colorChange.x, colorChange.y);

                if (colorChange.player == myPlayerNumber) {
                    changes.addScore();
                } else {
                    changes.removeScore();
                }

                subject.onNext(changes);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Game onCompleted called");
            }
        });
    }

    public PublishSubject<ScaledField> connect() {
        return subject;
    }

    private ScaledField getField(int x, int y) {
        return game[x / SCALE][y / SCALE];
    }

    public ScaledField[][] getBoard() {
        return game;
    }

    public ScaledField createField(int x, int y, NetworkClient client) {
        ScaledField scaledField = fillScaledField(x * SCALE, y * SCALE, client);
        game[x][y] = scaledField;
        return scaledField;
    }

    private ScaledField fillScaledField(int xStart, int yStart, NetworkClient client) {
        for (int xZoomed = xStart; xZoomed < xStart + SCALE; xZoomed++) {
            for (int yZoomed = yStart; yZoomed < yStart + SCALE; yZoomed++) {
                if (!client.isWalkable(xZoomed, yZoomed)) {
                    return new ScaledField(xStart, yStart, false);
                }
            }
        }
        return new ScaledField(xStart, yStart, true);
    }
}
