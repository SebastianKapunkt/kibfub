package de.htw.sebastiankapunkt.kipfub.model;

import de.htw.sebastiankapunkt.kipfub.game.GameField;

public class ScaledField {
    public final int fromX;
    public final int toX;
    public final int fromY;
    public final int toY;
    public final boolean isWalkable;

    private int score = 10000;

    public ScaledField(int x, int y, boolean isWalkable) {
        this.fromX = x;
        this.toX = x + GameField.SCALED;
        this.fromY = y;
        this.toY = y + GameField.SCALED;
        this.isWalkable = isWalkable;
    }

    public void addScore(int value) {
        score += value;
    }

    public void removeScore(int value) {
        score += value;
    }
}
