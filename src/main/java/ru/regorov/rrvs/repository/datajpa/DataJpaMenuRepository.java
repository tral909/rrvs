package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.repository.MenuRepository;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DataJpaMenuRepository implements MenuRepository {

    @Autowired
    CrudMenuRepository menuRepository;

    @Override
    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getRef(int id) {
        return menuRepository.getOne(id);
    }

    @Override
    public void appendDish(int menuId, int dishId) {
        menuRepository.appendDish(menuId, dishId);
    }

    @Override
    public void deleteDish(int menuId, int dishId) {
        menuRepository.deleteDish(menuId, dishId);
    }

    @Override
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return menuRepository.save(menu);
    }

    @Override
    public void update(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        int id = menu.getId();
        checkNotFoundWithId(menuRepository.findById(id).orElse(null), id);
        menuRepository.save(menu);
    }

    @Override
    public void delete(int id) {
        menuRepository.delete(id);
    }
}