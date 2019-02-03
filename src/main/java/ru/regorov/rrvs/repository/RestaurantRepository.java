package ru.regorov.rrvs.repository;

import org.springframework.stereotype.Repository;
import ru.regorov.rrvs.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    void delete(int id);
}
