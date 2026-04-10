package sn.khadija.l2gl.app.model;

@FunctionalInterface
public interface Comparateur<T> {
    int comparer(T a, T b);
}