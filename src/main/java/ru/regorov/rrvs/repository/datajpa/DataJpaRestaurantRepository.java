package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.repository.RestaurantRepository;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

    @Autowired
    CrudRestaurantRepository restaurantRepo;

    @Override
    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepo.findById(id).orElse(null), id);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Menu> findByRestIdMenus(int id) {
        return restaurantRepo.findByRestIdMenus(id);
    }

    @Override
    public List<Dish> findByRestIdAndMenuIdDishes(int restId, int menuId) {
        return restaurantRepo.findByRestIdAndMenuIdDishes(restId, menuId);
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepo.save(restaurant);
    }

    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepo.save(restaurant), restaurant.getId());
    }
    @Override
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepo.delete(id) != 0, id);
    }
}
