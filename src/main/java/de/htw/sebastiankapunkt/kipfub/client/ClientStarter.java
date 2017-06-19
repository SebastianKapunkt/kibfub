package de.htw.sebastiankapunkt.kipfub.client;

public class ClientStarter {
    public static void main(String[] args) {
        String host = "localhost";

        new Thread(() -> {
            new KipFubClient(host, "Name1");
        }).start();
        new Thread(() -> {
            new KipFubClient(host, "Name2");
        }).start();
        new Thread(() -> {
            new KipFubClient(host, "Name3");
        }).start();
    }
}
