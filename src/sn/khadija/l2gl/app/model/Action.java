package sn.khadija.l2gl.app.model;

@FunctionalInterface
public interface Action<T> {
    void executer(T t);
}