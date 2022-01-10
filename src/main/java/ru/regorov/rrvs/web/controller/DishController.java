package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.repository.DishRepository;
import ru.regorov.rrvs.util.ValidationUtil;
import ru.regorov.rrvs.web.json.JsonUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(DishController.REST_URL)
public class DishController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/dishes";

    @Autowired
    private DishRepository dishRepo;

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
    public ResponseEntity<String> create(@Valid @RequestBody Dish dish, BindingResult result) {
        log.info("create {}", dish);
        ValidationUtil.checkNew(dish);
        if (result.hasErrors()) {
            return ValidationUtil.handleValidationErrors(result);
        }
        Dish created = dishRepo.create(dish);
        return new ResponseEntity<>(JsonUtil.writeValue(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody Dish dish, BindingResult result) {
        log.info("update {} with id {}", dish, id);
        assureIdConsistent(dish, id);
        if (result.hasErrors()) {
            return ValidationUtil.handleValidationErrors(result);
        }
        dishRepo.update(dish);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete {}", id);
        dishRepo.delete(id);
    }
}
