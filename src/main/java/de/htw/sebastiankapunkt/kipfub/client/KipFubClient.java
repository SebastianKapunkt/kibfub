package de.htw.sebastiankapunkt.kipfub.client;

import de.htw.sebastiankapunkt.kipfub.game.GameField;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

import java.util.Random;

public class KipFubClient {
    private static Random rand = new Random();

    public KipFubClient(String host, String name) {
        int x = 512;
        int y = 512;

        NetworkClient networkClient = new NetworkClient(host, name);
        int playernumber = networkClient.getMyPlayerNumber(); // 0 = rot, 1 = grün, 2 = blau
        GameField game = new GameField(playernumber);

        int rgb = networkClient.getBoard(x, y); // 0-1023 ->
        int b = rgb & 255;
        int g = (rgb >> 8) & 255;
        int r = (rgb >> 16) & 255;

        networkClient.getInfluenceRadiusForBot(0); // -> 40

        networkClient.getScore(0); // Punkte von rot

        game.fillInitialField(networkClient);


        long i = System.currentTimeMillis();

        ColorChange colorChange;
        while (networkClient.isAlive()) {
            if ((colorChange = networkClient.pullNextColorChange()) != null) {
                game.applyColorChange(colorChange);
            }

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
