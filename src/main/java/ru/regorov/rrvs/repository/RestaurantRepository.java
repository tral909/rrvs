package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository extends AbstractRepository<Restaurant> {
    Restaurant getRef(int id);

    List<Menu> findByRestaurantIdMenus(int restId);

    List<Dish> findByRestaurantIdAndMenuIdDishes(int restId, int menuId);

    List<Menu> findByRestaurantIdAndDateMenus(int restId, LocalDate date);
}
