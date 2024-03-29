package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

@RestController
@RequestMapping(MenuController.REST_URL)
public class MenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/menus";

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private RestaurantRepository restRepo;

    @Autowired
    private DishRepository dishRepo;

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
    public MenuTo create(@RequestBody MenuTo menuTo) {
        log.info("create {}", menuTo);
        checkNew(menuTo);
        int restId = menuTo.getRestId();
        Restaurant restaurant = restRepo.getRef(restId);
        checkNotFoundWithId(restaurant, restId);
        return asTo(menuRepo.create(createFromTo(menuTo, restaurant)));
    }

    @PostMapping("/{menuId}" + DishController.REST_URL + "/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void appendDishToMenu(@PathVariable Integer menuId, @PathVariable Integer dishId) {
        log.info("append dishId {} to menuId {}", dishId, menuId);
        /*Menu menu = menuRepo.getRef(menuId);
        Dish dish = dishRepo.getRef(dishId);
        menu.getDishes().add(dish);
        menuRepo.update(menu);*/
        menuRepo.appendDish(menuId, dishId);
    }

    @DeleteMapping("/{menuId}" + DishController.REST_URL + "/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishFromMenu(@PathVariable Integer menuId, @PathVariable Integer dishId) {
        log.info("delete dish {} from menu {}", dishId, menuId);
        /*Dish dish = dishRepo.getRef(dishId);
        Menu menu = menuRepo.getRef(menuId);
        Set<Dish> dishes = menu.getDishes();
        dishes.remove(dish);
        menu.setDishes(dishes);
        menuRepo.update(menu);*/
        menuRepo.deleteDish(menuId, dishId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete {}", id);
        menuRepo.delete(id);
    }
}
