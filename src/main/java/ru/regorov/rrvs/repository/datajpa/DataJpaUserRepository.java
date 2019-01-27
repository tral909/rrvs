package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;

import java.util.List;

@Repository
public class DataJpaUserRepository implements UserRepository {

    @Autowired
    private CrudUserRepository userRepo;

    @Override
    public User get(int id) {
        return userRepo.getOne(id);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }
}
