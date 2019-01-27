package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.User;

import java.util.List;

public interface UserRepository {
    User get(int id);

    User save(User user);

    void delete(int id);

    List<User> getAll();
}
