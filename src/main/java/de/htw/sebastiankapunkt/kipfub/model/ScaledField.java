package de.htw.sebastiankapunkt.kipfub.model;

public class ScaledField {
    public final int x;
    public final int y;
    public final boolean isWalkable;

    public static double maxScore = 12;
    public double score = maxScore / 2;

    public ScaledField(int x, int y, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.isWalkable = isWalkable;
    }

    public void addScore(float value) {
        if (score + value <= maxScore) {
            score += value;
        }
    }

    public void removeScore(float value) {
        if (score - value >= 0) {
            score -= value;
        }
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScaledField that = (ScaledField) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
