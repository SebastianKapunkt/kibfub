package de.htw.sebastiankapunkt.kipfub.representation;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.model.Tuple3;
import de.htw.sebastiankapunkt.kipfub.pathfinding.Node;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import javafx.application.Application;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Map;

public class ViewController {

    private App app;

    public ViewController() {
        new Thread(() -> Application.launch(App.class)).start();
        app = App.waitForStart();
        app.drawGrid();
    }

    public void drawScaledField(ScaledField field) {
        if (app != null) {
            app.drawScaledField(field);
        }
    }

    public void drawPath(LinkedList<Node> nodes) {
        System.out.print(nodes.size());
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        app.drawPath(nodes);
    }

    public void drawNode(Node node) {
        app.drawNode(node);
    }

    public void drawSum(Map<Node, Double> sum) {
        app.drawSum(sum);
    }

    public void observe(PublishSubject<ScaledField> connect) {
        connect
                .map(scaledField -> new Tuple3<>(
                                new Color(
                                        scaledField.score / ScaledField.maxScore,
                                        scaledField.score / ScaledField.maxScore,
                                        scaledField.score / ScaledField.maxScore,
                                        1),
                                scaledField.fromX,
                                scaledField.fromY
                        )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new DisposableObserver<Tuple3<Color, Integer, Integer>>() {
                    @Override
                    public void onNext(Tuple3<Color, Integer, Integer> colorChange) {
                        app.applyChange(colorChange.item1, colorChange.item2, colorChange.item3);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("ViewController onCompleted called");
                    }
                });
    }
}
