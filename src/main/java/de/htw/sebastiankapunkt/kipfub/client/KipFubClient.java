package de.htw.sebastiankapunkt.kipfub.client;

import de.htw.sebastiankapunkt.kipfub.game.GameController;
import lenz.htw.kipifub.net.NetworkClient;

import java.util.Random;

public class KipFubClient {
    private static Random rand = new Random();

    public KipFubClient(String host, String name) {
        NetworkClient networkClient = new NetworkClient(host, name);
        GameController controller = new GameController(networkClient);
        controller.startObserving();

        long i = System.currentTimeMillis();

        while (networkClient.isAlive()) {
            if (System.currentTimeMillis() - i > 1000) {
                i = System.currentTimeMillis();
//                networkClient.setMoveDirection(0, 512.0f - game.getBots()[playernumber][0].getX(), 512.0f - game.getBots()[playernumber][0].getY());
//                networkClient.setMoveDirection(1, 512.0f - game.getBots()[playernumber][1].getX(), 512.0f - game.getBots()[playernumber][1].getY());
//                networkClient.setMoveDirection(2, 512.0f - game.getBots()[playernumber][2].getX(), 512.0f - game.getBots()[playernumber][2].getY());
                networkClient.setMoveDirection(0, randomDirection(), randomDirection());
                networkClient.setMoveDirection(1, randomDirection(), randomDirection());
                networkClient.setMoveDirection(2, randomDirection(), randomDirection());
            }
        }
    }

    private static float randomDirection() {
        return 1 - rand.nextFloat() * 2;
    }

}
