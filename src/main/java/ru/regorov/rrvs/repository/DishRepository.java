package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Dish;

import java.util.List;

public interface DishRepository {
    Dish get(int id);

    List<Dish> getAll();

    Dish create(Dish dish);

    void update(Dish dish);

    void delete(int delete);
}
