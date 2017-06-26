package de.htw.sebastiankapunkt.kipfub.model;

public class Bot {
    public final int type;
    public final int playerNumber;
    public int x;
    public int y;

    public Bot(int type, int playerNumber) {
        this.type = type;
        this.playerNumber = playerNumber;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
