package de.htw.sebastiankapunkt.kipfub.model;

public class Tuple3<S, T, U> {

    public final S item1;
    public final T item2;
    public final U item3;

    public Tuple3(S item1, T item2, U item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }
}
