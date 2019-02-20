package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.repository.DishRepository;
import ru.regorov.rrvs.repository.MenuRepository;
import ru.regorov.rrvs.repository.RestaurantRepository;
import ru.regorov.rrvs.to.MenuTo;

import java.util.List;

import static ru.regorov.rrvs.util.MenuUtil.asTo;
import static ru.regorov.rrvs.util.MenuUtil.createFromTo;
import static ru.regorov.rrvs.util.ValidationUtil.checkNew;
import static ru.regorov.rrvs.util.ValidationUtil.checkNotFoundWithId;

//TODO релизовать остальные методы
//TODO сделать тесты
//TODO добавить в доку
@RestController
@RequestMapping(MenuController.REST_URL)
public class MenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/menus";

    @Autowired
    MenuRepository menuRepo;

    @Autowired
    RestaurantRepository restRepo;

    @Autowired
    DishRepository dishRepo;

    @GetMapping
    public List<MenuTo> getAll() {
        log.info("getAll");
        return asTo(menuRepo.getAll());
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable Integer id) {
        log.info("get {}", id);
        return menuRepo.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Menu create(@RequestBody MenuTo menuTo) {
        log.info("create {}", menuTo);
        checkNew(menuTo);
        int restId = menuTo.getRestId();
        Restaurant restaurant = restRepo.getRef(restId);
        checkNotFoundWithId(restaurant, restId);
        return menuRepo.create(createFromTo(menuTo, restaurant));
    }

    @PostMapping("/{menuId}" + DishController.REST_URL + "/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void appendDishToMenu(@PathVariable Integer menuId, @PathVariable Integer dishId) {
        log.info("append dishId {} to menuId {}", dishId, menuId);
        Menu menu = menuRepo.getRef(menuId);
        Dish dish = dishRepo.getRef(dishId);
        menu.getDishes().add(dish);
        menuRepo.update(menu);
    }

    @DeleteMapping("/{menuId}" + DishController.REST_URL + "/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishFromMenu(@PathVariable Integer menuId, @PathVariable Integer dishId) {

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {

    }
}
