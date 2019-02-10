package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
    public Menu create(Menu menu) {
        return null;
    }

    @Override
    public void update(Menu menu) {

    }

    @Override
    public void delete(int id) {

    }
}