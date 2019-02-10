package ru.regorov.rrvs.repository;

import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.to.VoteTo;

import java.util.List;

public interface VoteRepository {
    Vote get(int id, int userId);

    List<Vote> getAll(int userId);

    Vote create(VoteTo voteTo, int userId);

    void update(VoteTo voteTo, int userId);

    void delete(int id, int userId);
}
