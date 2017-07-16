package de.htw.sebastiankapunkt.kipfub.client;

import de.htw.sebastiankapunkt.kipfub.game.GameController;
import lenz.htw.kipifub.net.NetworkClient;

import java.util.Random;

public class KipFubClient {
    private static Random rand = new Random();

    public KipFubClient(String host, String name) {
        NetworkClient networkClient = new NetworkClient(host, name);
        GameController controller = new GameController(networkClient);
        try {
            controller.initialize();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
