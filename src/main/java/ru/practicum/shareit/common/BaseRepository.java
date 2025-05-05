package ru.practicum.shareit.common;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Общий базовый класс для CRUD операций
 */
@RequiredArgsConstructor
public abstract class BaseRepository<T extends CommonIdGenerator> {
    public final Map<Long, T> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public Optional<T> findById(final Long id) {
        T t = storage.get(id);
        return Optional.ofNullable(t);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public T create(final T entity) {
        long id = idGenerator.incrementAndGet();
        entity.setId(id);
        storage.put(id, entity);
        return storage.get(id);
    }

    public T update(final T entity) {
        Long entityId = entity.getId();
        if (!storage.containsKey(entityId)) {
            throw new IllegalArgumentException("Entity with id=" + entityId + " not found");
        }
        storage.put(entityId, entity);
        return storage.get(entityId);
    }

    public boolean delete(final Long id) {
        return storage.remove(id) != null;
    }
}