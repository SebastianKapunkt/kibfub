package de.htw.sebastiankapunkt.kipfub.model;

import de.htw.sebastiankapunkt.kipfub.game.GameField;

public class ScaledField {
    public final int fromX;
    public final int toX;
    public final int fromY;
    public final int toY;
    public final boolean isWalkable;

    public static int maxScore = GameField.SCALED;
    private int score = maxScore;

    public ScaledField(int x, int y, boolean isWalkable) {
        this.fromX = x;
        this.toX = x + GameField.SCALED;
        this.fromY = y;
        this.toY = y + GameField.SCALED;
        this.isWalkable = isWalkable;
    }

    public void addScore() {
        if (score < maxScore) {
            score += GameField.SCALED/3;
        }
    }

    public void removeScore() {
        if (score > 0) {
            score -= GameField.SCALED/3;
        }
    }

    public int getScore() {
        return score;
    }
}