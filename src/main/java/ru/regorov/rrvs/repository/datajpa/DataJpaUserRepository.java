package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DataJpaUserRepository implements UserRepository {

    @Autowired
    private CrudUserRepository userRepo;

    @Override
    public User getByLogin(String username) {
        return userRepo.findByLogin(username);
    }

    @Override
    public User get(int id) {
        return checkNotFoundWithId(userRepo.findById(id).orElse(null), id);
    }

    @Override
    public User getRef(int id) {
        return userRepo.getOne(id);
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepo.save(user);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepo.save(user), user.getId());
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(userRepo.delete(id) != 0, id);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }
}