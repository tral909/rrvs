package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.User;

public interface UserRepository extends AbstractRepository<User>{
    User getRef(int id);

    User getByLogin(String username);
}
