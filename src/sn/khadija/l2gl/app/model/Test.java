package sn.khadija.l2gl.app.model;

@FunctionalInterface
public interface Test<T> {
    boolean tester(T t);
}