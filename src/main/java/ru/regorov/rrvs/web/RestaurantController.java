package ru.regorov.rrvs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.repository.RestaurantRepository;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(RestaurantController.REST_URL)
public class RestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/restaurants";

    @Autowired
    RestaurantRepository restaurantRepo;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantRepo.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Integer id) {
        log.info("get restaurant {}", id);
        return restaurantRepo.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        return restaurantRepo.create(restaurant);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable Integer id) {
        log.info("update restaurant {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantRepo.update(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete restaurant {}", id);
        restaurantRepo.delete(id);
    }
}
