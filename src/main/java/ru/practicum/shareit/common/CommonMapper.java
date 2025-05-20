package ru.practicum.shareit.common;

import java.util.List;
import java.util.stream.Collectors;

public interface CommonMapper<E, D> {
    E toEntity(D d);

    D toDto(E e);

    default List<E> toEntityList(List<D> d) {
        return d.stream().map(this::toEntity).collect(Collectors.toList());
    }

    default List<D> toDtoList(List<E> e) {
        return e.stream().map(this::toDto).collect(Collectors.toList());
    }
}
