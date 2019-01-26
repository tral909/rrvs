package ru.regorov.rrvs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.regorov.rrvs.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
