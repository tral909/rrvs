package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.repository.VoteRepository;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DataJpaVoteRepository implements VoteRepository {
    @Autowired
    CrudVoteRepository repo;

    @Override
    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repo.findById(id).filter(vote -> vote.getUser().getId() == userId).orElse(null), id);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return repo.getAll(userId);
    }

    @Override
    public Vote create(Vote dish) {
        return null;
    }

    @Override
    public void update(Vote dish) {

    }

    @Override
    public void delete(int id, int userId) {

    }
}
