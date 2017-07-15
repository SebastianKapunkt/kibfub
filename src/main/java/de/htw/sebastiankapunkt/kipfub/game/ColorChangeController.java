package de.htw.sebastiankapunkt.kipfub.game;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

public class ColorChangeController {

    private PublishSubject<ColorChange> subject = PublishSubject.create();

    public PublishSubject<ColorChange> connect() {
        return subject;
    }

    public void observeColorChange(NetworkClient client) {
        Flowable.create((FlowableOnSubscribe<ColorChange>) emitter -> {
            ColorChange colorChange;

            while (client.isAlive()) {
                try {
                    if ((colorChange = client.pullNextColorChange()) != null) {
                        subject.onNext(colorChange);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            subject.onComplete();
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe();
    }
}
