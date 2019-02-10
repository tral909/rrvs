package ru.regorov.rrvs.repository;

import java.util.List;

public interface AbstractRepository<T> {
    T get(int id);

    List<T> getAll();

    T create(T dish);

    void update(T dish);

    void delete(int id);
}
