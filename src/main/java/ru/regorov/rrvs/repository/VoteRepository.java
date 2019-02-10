package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Vote;

import java.util.List;

public interface VoteRepository {
    Vote get(int id, int userId);

    List<Vote> getAll(int userId);

    Vote create(Vote vote);

    void update(Vote vote);

    void delete(int id, int userId);
}
