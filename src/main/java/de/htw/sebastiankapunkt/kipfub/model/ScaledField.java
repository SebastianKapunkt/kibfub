package de.htw.sebastiankapunkt.kipfub.model;

import de.htw.sebastiankapunkt.kipfub.game.GameField;

public class ScaledField {
    public final int fromX;
    public final int toX;
    public final int fromY;
    public final int toY;
    public final boolean isWalkable;

    private int score = 10000;

    private ScaledField top;
    private ScaledField right;
    private ScaledField bottom;
    private ScaledField let;

    public ScaledField(int x, int y, boolean isWalkable) {
        this.fromX = x;
        this.toX = x + GameField.SCALED;
        this.fromY = y;
        this.toY = y + GameField.SCALED;
        this.isWalkable = isWalkable;
    }

    public void setTop(ScaledField top) {
        this.top = top;
    }

    public void setRight(ScaledField right) {
        this.right = right;
    }

    public void setBottom(ScaledField bottom) {
        this.bottom = bottom;
    }

    public void setLet(ScaledField let) {
        this.let = let;
    }

    public void addScore(int value) {
        score += value;
    }

    public void removeScore(int value) {
        score += value;
    }
}
