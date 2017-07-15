package de.htw.sebastiankapunkt.kipfub.client;

public class ClientStarter {
    public static void main(String[] args) {
        String host = "192.168.2.100";

        new Thread(() -> {
            new KipFubClient(host, "Name1");
        }).start();
//        new Thread(() -> {
//            new RandomClient(host, "Random1");
//        }).start();
//        new Thread(() -> {
//            new RandomClient(host, "Random2");
//        }).start();
    }
}
