package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.repository.RestaurantRepository;
import ru.regorov.rrvs.to.MenuTo;
import ru.regorov.rrvs.to.RestaurantTo;
import ru.regorov.rrvs.util.MenuUtil;
import ru.regorov.rrvs.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;
import static ru.regorov.rrvs.util.ValidationUtil.checkNew;

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
        return RestaurantUtil.asTo(restaurantRepo.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantTo getRest(@PathVariable Integer id) {
        log.info("get restaurant {}", id);
        return RestaurantUtil.asTo(restaurantRepo.get(id));
    }

    //TODO сделать тест
    @GetMapping("/{restId}" + MenuController.REST_URL)
    public List<MenuTo> getMenusByRestId(@PathVariable Integer restId) {
        log.info("getMenusByRestId {}", restId);
        return MenuUtil.asTo(restaurantRepo.findByRestIdMenus(restId));
    }

    //TODO сделать тест
    @GetMapping("/{restId}" + MenuController.REST_URL + "/filter")
    public List<Menu> getMenusByRestIdAndDate(@PathVariable Integer restId,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             @RequestParam(value = "date") LocalDate date) {
        log.info("getMenusByRestIdAndDate (restId={}, date={})", restId, date);
        return restaurantRepo.findByRestIdAndDateMenus(restId, date);
    }

    //TODO сделать тест
    @GetMapping("/{restId}" + MenuController.REST_URL + "/{menuId}")
    public List<Dish> getDishesByRestIdAndMenuId(@PathVariable Integer restId, @PathVariable Integer menuId) {
        log.info("getDishesByRestIdAndMenuId (rest={}, menu={})", restId, menuId);
        return restaurantRepo.findByRestIdAndMenuIdDishes(restId, menuId);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRest(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
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
