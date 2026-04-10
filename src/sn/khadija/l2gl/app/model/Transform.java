package sn.khadija.l2gl.app.model;

@FunctionalInterface
public interface Transform<T, R> {
    R appliquer(T t);
}