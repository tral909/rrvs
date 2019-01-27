package ru.regorov.rrvs.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.regorov.rrvs.model.User;

public interface CrudUserRepository extends JpaRepository<User, Integer> {

}
