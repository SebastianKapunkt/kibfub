package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.game.GameField;
import de.htw.sebastiankapunkt.kipfub.game.HeatMapController;
import de.htw.sebastiankapunkt.kipfub.model.Brush;
import de.htw.sebastiankapunkt.kipfub.model.Node;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import lenz.htw.kipifub.net.NetworkClient;

import java.util.LinkedList;
import java.util.Random;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;
import static de.htw.sebastiankapunkt.kipfub.game.HeatMapController.HEATMAP_MODIFIER;

public class PathMover {

    private Brush observingBrush;
    private GameField game;
    private NetworkClient client;
    private static Random rand = new Random();

    private LinkedList<Node> currentPath = new LinkedList<>();
    private long timestamp;
    private boolean isPathfinding = true;

    private Node currentGoal = new Node(32, 15);

    private PublishSubject<LinkedList<Node>> subject = PublishSubject.create();

    public PathMover(Brush observingBrush, GameField game, NetworkClient client) {
        this.observingBrush = observingBrush;
        this.game = game;
        this.client = client;

        timestamp = System.currentTimeMillis();
    }

    public void observe(PublishSubject<Brush> connect) {
        connect
                .filter(brush -> brush.equals(observingBrush))
                .doOnNext(brush -> {
                    if (!currentPath.isEmpty()) {
                        Node current = currentPath.getLast();

                        if (isNearNextNode(brush, current)) {
                            moveBrush(currentPath.getLast());
                            currentPath.removeLast();
                        }

                        moveBrush(current);
                    }
                })
                .doOnNext(brush -> {
                    if (currentPath.isEmpty()) {
                        if (System.currentTimeMillis() - timestamp > 1000) {
                            timestamp = System.currentTimeMillis();
                            client.setMoveDirection(brush.type, randomDirection(), randomDirection());
                        }
                    }
                })
                .doOnNext(brush -> {
                    if (currentPath.isEmpty()) {
                        HeatMapController heatMap = new HeatMapController(game.getBoard());
                        heatMap.createHeatMap();
                        Node highest = heatMap.getHighest();

                        System.out.println(highest.x + " " + highest.y);
                        currentGoal = new Node(highest.x * SCALE * HEATMAP_MODIFIER, highest.y * SCALE * HEATMAP_MODIFIER);

                        isPathfinding = true;
                    }
                })
                .doOnNext(brush -> {
                    if (isPathfinding) {
                        isPathfinding = false;
                        Node start = new Node(brush.x / SCALE, brush.y / SCALE);
                        Pathfinder pathfinder = new Pathfinder(game.getBoard());
                        currentPath = pathfinder.aStar(start, currentGoal);
                        subject.onNext(currentPath);
                        currentPath.removeLast();
                        moveBrush(currentPath.getLast());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new DisposableObserver<Brush>() {
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

    public boolean isNearNextNode(Brush brush, Node current) {
        int threshold = 16;
        return current.x * SCALE - threshold < brush.x
                && brush.x < current.x * SCALE + threshold
                && current.y * SCALE - threshold < brush.y
                && brush.y < current.y * SCALE + threshold;
    }

    private void moveBrush(Node first) {
        float xDi = first.x * SCALE - observingBrush.x;
        float yDi = first.y * SCALE - observingBrush.y;

//        System.out.println(xDi + " " + yDi);

        client.setMoveDirection(observingBrush.type, xDi, yDi);
    }

    public PublishSubject<LinkedList<Node>> connect() {
        return subject;
    }

    private float randomDirection() {
        return 1 - rand.nextFloat() * 2;
    }
}
