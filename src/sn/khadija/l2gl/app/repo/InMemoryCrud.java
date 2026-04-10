package sn.khadija.l2gl.app.repo;

import sn.khadija.l2gl.app.model.Identifiable;
import java.util.*;

public class InMemoryCrud<T extends Identifiable> implements Crud<T> {

    private final Map<Long, T> storage = new HashMap<>();

    @Override
    public void create(T entity) {
        if (storage.containsKey(entity.getId()))
            throw new IllegalArgumentException("ID déjà existant : " + entity.getId());
        storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<T> read(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(T entity) {
        if (!storage.containsKey(entity.getId()))
            throw new IllegalArgumentException("ID inexistant : " + entity.getId());
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) {
        if (!storage.containsKey(id))
            throw new IllegalArgumentException("ID inexistant : " + id);
        storage.remove(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }
}