package de.htw.sebastiankapunkt.kipfub.model;

public class Brush {
    public final int type;
    public final int playerNumber;
    public int x;
    public int y;

    public Brush(int type, int playerNumber) {
        this.type = type;
        this.playerNumber = playerNumber;
    }
}
