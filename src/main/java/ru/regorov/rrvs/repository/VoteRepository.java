package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository {
    Vote get(int id, int userId);

    List<Vote> getAll(int userId);

    void save(Vote vote);

    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

    void delete(int id, int userId);
}
