package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.Brush;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import lenz.htw.kipifub.ColorChange;

public class BrushController {
    private Brush[][] brushes = new Brush[3][3];
    private PublishSubject<Brush> subject = PublishSubject.create();
    private int myPlayerNumber;

    public BrushController(int playerNumber) {
        myPlayerNumber = playerNumber;

        brushes[0][0] = new Brush(0, 0);
        brushes[0][1] = new Brush(1, 0);
        brushes[0][2] = new Brush(2, 0);

        brushes[1][0] = new Brush(0, 1);
        brushes[1][1] = new Brush(1, 1);
        brushes[1][2] = new Brush(2, 1);

        brushes[2][0] = new Brush(0, 2);
        brushes[2][1] = new Brush(1, 2);
        brushes[2][2] = new Brush(2, 2);
    }

    public void observe(PublishSubject<ColorChange> connect) {
        connect
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new DisposableObserver<ColorChange>() {
                    @Override
                    public void onNext(ColorChange colorChange) {
                        brushes[colorChange.player][colorChange.bot].x = colorChange.x;
                        brushes[colorChange.player][colorChange.bot].y = colorChange.y;
                        subject.onNext(brushes[colorChange.player][colorChange.bot]);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("BrushController onCompleted called");
                    }
                });
    }

    public PublishSubject<Brush> connect() {
        return subject;
    }

    public Brush getMyBrushOne() {
        return brushes[myPlayerNumber][0];
    }

    public Brush getMyBrushTwo() {
        return brushes[myPlayerNumber][1];
    }

    public Brush getMyBrushThree() {
        return brushes[myPlayerNumber][2];
    }
}
