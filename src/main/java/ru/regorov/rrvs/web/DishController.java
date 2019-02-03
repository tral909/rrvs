package ru.regorov.rrvs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.repository.DishRepository;

import java.util.List;

@RestController
@RequestMapping(DishController.REST_URL)
public class DishController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/dishes";

    @Autowired
    DishRepository dishRepo;

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll dishes");
        return dishRepo.getAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable Integer id) {
        log.info("get dish {}", id);
        return dishRepo.get(id);
    }

}
