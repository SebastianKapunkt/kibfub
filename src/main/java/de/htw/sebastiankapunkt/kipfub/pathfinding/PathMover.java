package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.game.GameField;
import de.htw.sebastiankapunkt.kipfub.model.Brush;
import de.htw.sebastiankapunkt.kipfub.model.Node;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import lenz.htw.kipifub.net.NetworkClient;

import java.util.LinkedList;
import java.util.Random;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALED;

public class PathMover {

    private Brush observingBrush;
    private GameField game;
    private NetworkClient client;
    private long timestamp;

    private boolean isPathfinding = true;
    private static int treshhold = 16;
    private LinkedList<Node> currentPath;
    private static Random rand = new Random();

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
                    if (isPathfinding) {
                        isPathfinding = false;
                        Node start = new Node(brush.x / SCALED, brush.y / SCALED);
                        Node goal = new Node(32, 15);
                        Pathfinding pathfinding = new Pathfinding(game.getBoard());
                        currentPath = pathfinding.aStar(start, goal);
                        subject.onNext(currentPath);
                        currentPath.removeLast();
                        moveBrush(currentPath.getLast());
                    }
                })
                .subscribe(new DisposableObserver<Brush>() {
                    @Override
                    public void onNext(Brush brush) {
                        if (!currentPath.isEmpty()) {
                            Node current = currentPath.getLast();

                            if (isNearNextNode(brush, current)) {
                                moveBrush(currentPath.getLast());
                                currentPath.removeLast();
                            }

                            moveBrush(current);
                        } else {
                            if (System.currentTimeMillis() - timestamp > 1000) {
                                timestamp = System.currentTimeMillis();
                                client.setMoveDirection(brush.type, randomDirection(), randomDirection());
                            }
                        }
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
        return current.x * SCALED - treshhold < brush.x
                && brush.x < current.x * SCALED + treshhold
                && current.y * SCALED - treshhold < brush.y
                && brush.y < current.y * SCALED + treshhold;
    }

    private void moveBrush(Node first) {
        float xDi = first.x * SCALED - observingBrush.x;
        float yDi = first.y * SCALED - observingBrush.y;

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
