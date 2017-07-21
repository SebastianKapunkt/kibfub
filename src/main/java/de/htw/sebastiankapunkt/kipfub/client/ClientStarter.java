package de.htw.sebastiankapunkt.kipfub.client;

public class ClientStarter {
    public static void main(String[] args) {
//        String host = "192.168.2.100";
//        String host = "141.45.209.243";
//        String host = "141.45.201.243";
        String host = "localhost";

        new Thread(() -> {
            new KipFubClient(host, "TramRam");
        }).start();
        new Thread(() -> {
            new RandomClient(host, "Random1");
        }).start();
        new Thread(() -> {
            new RandomClient(host, "Random2");
        }).start();
    }
}
