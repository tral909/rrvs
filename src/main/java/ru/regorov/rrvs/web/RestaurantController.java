package ru.regorov.rrvs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.repository.RestaurantRepository;
import ru.regorov.rrvs.to.MenuTo;
import ru.regorov.rrvs.to.RestaurantTo;

import java.util.List;

import static ru.regorov.rrvs.util.MenuUtil.asTo;
import static ru.regorov.rrvs.util.RestaurantUtil.asTo;
import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(RestaurantController.REST_URL)
public class RestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/restaurants";

    @Autowired
    RestaurantRepository restaurantRepo;

    @GetMapping
    public List<RestaurantTo> getAllRests() {
        log.info("getAll restaurants");
        return asTo(restaurantRepo.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantTo getRest(@PathVariable Integer id) {
        log.info("get restaurant {}", id);
        return asTo(restaurantRepo.get(id));
    }

    @GetMapping("/{restId}" + MenuController.REST_URL)
    public List<MenuTo> getAllMenusByRestId(@PathVariable Integer restId) {
        return asTo(restaurantRepo.findByRestIdMenus(restId));
    }

    @GetMapping("/{mainId}" + MenuController.REST_URL + "/{subId}")
    public List<Menu> getMenu() {
        //TODO реализовать этот метод
        //TODO подумать об REST API в общем, возможно сделать описание в swagger yaml файле
        //TODO сделать тесты на getAllMenusByRestId, getMenu и в других моделях на текущие контролы
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRest(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        return restaurantRepo.create(restaurant);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateRest(@RequestBody Restaurant restaurant, @PathVariable Integer id) {
        log.info("update restaurant {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantRepo.update(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRest(@PathVariable Integer id) {
        log.info("delete restaurant {}", id);
        restaurantRepo.delete(id);
    }
}
