package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Menu;

import java.util.List;

public interface MenuRepository {

    Menu get(int id);

    List<Menu> getAll();

    Menu create(Menu menu);

    void update(Menu menu);

    void delete(int id);
}
