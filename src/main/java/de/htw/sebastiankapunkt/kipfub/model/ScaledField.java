package de.htw.sebastiankapunkt.kipfub.model;

import de.htw.sebastiankapunkt.kipfub.game.GameField;

public class ScaledField {
    public final int fromX;
    public final int toX;
    public final int fromY;
    public final int toY;
    public final boolean isWalkable;

    public static double maxScore = 6;
    private double score = maxScore;

    public ScaledField(int x, int y, boolean isWalkable) {
        this.fromX = x;
        this.toX = x + GameField.SCALED;
        this.fromY = y;
        this.toY = y + GameField.SCALED;
        this.isWalkable = isWalkable;

        if (!isWalkable) {
            score = maxScore * 100;
        }
    }

    public void addScore() {
        if (score < maxScore) {
            score += 1;
        }
    }

    public void removeScore() {
        if (score > 0) {
            score -= 1;
        }
    }

    public double getScore() {
        return score;
    }
}
