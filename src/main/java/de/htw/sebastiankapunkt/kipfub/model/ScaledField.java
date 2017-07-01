package de.htw.sebastiankapunkt.kipfub.model;

public class ScaledField {
    public final int fromX;
    public final int toX;
    public final int fromY;
    public final int toY;

    private int score = 10000;

    private ScaledField top;
    private ScaledField right;
    private ScaledField bottom;
    private ScaledField let;

    public ScaledField(int size, int x, int y) {
        this.fromX = x * size;
        this.toX = size * x + size;
        this.fromY = y * size;
        this.toY = size * y + size;
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

    public void addScore(int value){
        score += value;
    }

    public void removeScore(int value){
        score += value;
    }
}
