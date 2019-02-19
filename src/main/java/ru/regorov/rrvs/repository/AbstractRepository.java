package ru.regorov.rrvs.repository;

import java.util.List;

public interface AbstractRepository<T> {
    T get(int id);

    List<T> getAll();

    T create(T item);

    void update(T item);

    void delete(int id);
}
