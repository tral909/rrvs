package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.repository.DishRepository;
import ru.regorov.rrvs.util.ValidationUtil;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(DishController.REST_URL)
public class DishController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/dishes";

    @Autowired
    DishRepository dishRepo;

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll");
        return dishRepo.getAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable Integer id) {
        log.info("get {}", id);
        return dishRepo.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dish create(@RequestBody Dish dish) {
        log.info("create {}", dish);
        ValidationUtil.checkNew(dish);
        return dishRepo.create(dish);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Dish dish) {
        log.info("update {} with id {}", dish, id);
        assureIdConsistent(dish, id);
        dishRepo.update(dish);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete {}", id);
        dishRepo.delete(id);
    }
}
