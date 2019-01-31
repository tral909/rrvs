package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.User;

import java.util.List;

public interface UserRepository {
    User get(int id);

    User create(User user);

    void update(User user);

    void delete(int id);

    List<User> getAll();
}
