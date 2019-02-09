package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    void delete(int id);

    List<Menu> findByRestIdMenus(int restId);

    List<Dish> findByRestIdAndMenuIdDishes(int restId, int menuId);

    List<Menu> findByRestIdAndDateMenus(int restId, LocalDate date);
}
