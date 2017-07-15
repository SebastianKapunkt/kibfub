package de.htw.sebastiankapunkt.kipfub.client;

import lenz.htw.kipifub.net.NetworkClient;

import java.util.Random;

public class RandomClient {
    private static Random rand = new Random();

    public RandomClient(String host, String name) {
        NetworkClient networkClient = new NetworkClient(host, name);

        long i = System.currentTimeMillis();

        while (networkClient.isAlive()) {
            if (System.currentTimeMillis() - i > 1000) {
                i = System.currentTimeMillis();
//                networkClient.setMoveDirection(0, randomDirection(), randomDirection());
//                networkClient.setMoveDirection(1, randomDirection(), randomDirection());
//                networkClient.setMoveDirection(2, randomDirection(), randomDirection());
            }
        }
    }

    private static float randomDirection() {
        return 1 - rand.nextFloat() * 2;
    }
}
