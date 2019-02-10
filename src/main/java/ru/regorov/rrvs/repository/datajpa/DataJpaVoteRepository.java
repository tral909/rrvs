package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.repository.VoteRepository;
import ru.regorov.rrvs.to.VoteTo;
import ru.regorov.rrvs.util.VoteUtil;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.checkNotFoundWithId;

@Repository
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
    public Vote create(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "vote must not be null");
        if (!voteTo.isNew()) {
            return null;
        }
        return voteRepo.save(constructVote(voteTo, userId));
    }

    @Override
    public void update(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "vote must not be null");
        voteRepo.save(constructVote(voteTo, userId));
    }

    private Vote constructVote(VoteTo voteTo, int userId) {
        User user = userRepo.findById(userId).orElse(null);
        checkNotFoundWithId(user, userId);
        int restId = voteTo.getRestId();
        Restaurant restaurant = restRepo.findById(voteTo.getRestId()).orElse(null);
        checkNotFoundWithId(restaurant, restId);
        return VoteUtil.createFromTo(voteTo, restaurant, user);
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepo.delete(id, userId) != 0, id);
    }
}
