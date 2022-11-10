package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Menu;

public interface MenuRepository extends AbstractRepository<Menu> {
    Menu getRef(int id);

    void appendDish(int menuId, int dishId);

    void deleteDish(int menuId, int dishId);
}
