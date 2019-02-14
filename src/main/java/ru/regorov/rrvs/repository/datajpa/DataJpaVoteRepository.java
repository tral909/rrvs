package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.regorov.rrvs.util.ValidationUtil.checkNotFoundWithId;

@Repository
@Transactional(readOnly = true)
public class DataJpaVoteRepository implements VoteRepository {
    @Autowired
    CrudVoteRepository voteRepo;

    @Autowired
    CrudUserRepository userRepo;

    @Autowired
    CrudRestaurantRepository restRepo;

    @Override
    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepo.findById(id).filter(vote -> vote.getUser().getId() == userId).orElse(null), id);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return voteRepo.getAll(userId);
    }

    @Override
    public Optional<Vote> findByUserIdAndDate(int userId, LocalDate date) {
        return voteRepo.findByUserIdAndDate(userId, date);
    }

    @Override
    @Transactional
    public void save(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        voteRepo.save(vote);
    }

    @Override
    @Transactional
    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepo.delete(id, userId) != 0, id);
    }
}
