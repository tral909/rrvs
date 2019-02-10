package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.regorov.rrvs.repository.DishRepository;
import ru.regorov.rrvs.to.DishTo;

import java.util.List;

import static ru.regorov.rrvs.util.DishUtil.asTo;

@RestController
@RequestMapping(DishController.REST_URL)
public class DishController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/dishes";

    @Autowired
    DishRepository dishRepo;

    @GetMapping
    public List<DishTo> getAll() {
        log.info("getAll dishes");
        return asTo(dishRepo.getAll());
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable Integer id) {
        log.info("get dish {}", id);
        return asTo(dishRepo.get(id));
    }
}
