package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.repository.MenuRepository;

import java.util.List;

@RestController
@RequestMapping(MenuController.REST_URL)
public class MenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/menus";

    @Autowired
    MenuRepository menuRepo;

    @GetMapping
    public List<Menu> getAll() {
        log.info("getAll menus");
        return menuRepo.getAll();
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable Integer id) {
        log.info("get menu {}", id);
        return menuRepo.get(id);
    }
}
