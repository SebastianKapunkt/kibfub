package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.Brush;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

public class PathMover {

    public PathMover() {
    }

    public void observe(PublishSubject<Brush> connect) {
        connect.subscribe(new DisposableObserver<Brush>() {
            @Override
            public void onNext(Brush brush) {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("PathMover onCompleted called");
            }
        });
    }
}
