package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.repository.RestaurantRepository;
import ru.regorov.rrvs.to.MenuTo;
import ru.regorov.rrvs.to.RestaurantTo;
import ru.regorov.rrvs.util.MenuUtil;
import ru.regorov.rrvs.util.RestaurantUtil;
import ru.regorov.rrvs.web.json.JsonUtil;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;
import static ru.regorov.rrvs.util.ValidationUtil.checkNew;
import static ru.regorov.rrvs.util.ValidationUtil.handleValidationErrors;

@RestController
@RequestMapping(RestaurantController.REST_URL)
public class RestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepo;

    @GetMapping
    public List<RestaurantTo> getAllRests() {
        log.info("getAll");
        return RestaurantUtil.asTo(restaurantRepo.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantTo getRest(@PathVariable Integer id) {
        log.info("get {}", id);
        return RestaurantUtil.asTo(restaurantRepo.get(id));
    }

    @GetMapping("/{restId}" + MenuController.REST_URL)
    public List<MenuTo> getMenusByRestId(@PathVariable Integer restId) {
        log.info("{}", restId);
        return MenuUtil.asTo(restaurantRepo.findByRestIdMenus(restId));
    }

    @GetMapping("/{restId}" + MenuController.REST_URL + "/filter")
    public List<Menu> getMenusByRestIdAndDate(@PathVariable Integer restId,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             @RequestParam(value = "date") LocalDate date) {
        log.info("restId={}, date={}", restId, date);
        return restaurantRepo.findByRestIdAndDateMenus(restId, date);
    }

    @GetMapping("/{restId}" + MenuController.REST_URL + "/{menuId}")
    public List<Dish> getDishesByRestIdAndMenuId(@PathVariable Integer restId, @PathVariable Integer menuId) {
        log.info("rest={}, menu={}", restId, menuId);
        return restaurantRepo.findByRestIdAndMenuIdDishes(restId, menuId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createRest(@Valid @RequestBody Restaurant restaurant, BindingResult result) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        if (result.hasErrors()) {
            return handleValidationErrors(result);
        }
        Restaurant created = restaurantRepo.create(restaurant);
        return new ResponseEntity<>(JsonUtil.writeValue(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRest(@Valid @RequestBody Restaurant restaurant, BindingResult result, @PathVariable Integer id) {
        log.info("update {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        if (result.hasErrors()) {
            return handleValidationErrors(result);
        }
        restaurantRepo.update(restaurant);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRest(@PathVariable Integer id) {
        log.info("delete {}", id);
        restaurantRepo.delete(id);
    }
}
