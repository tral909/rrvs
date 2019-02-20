package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Menu;

public interface MenuRepository extends AbstractRepository<Menu>{
    Menu getRef(int id);
}
