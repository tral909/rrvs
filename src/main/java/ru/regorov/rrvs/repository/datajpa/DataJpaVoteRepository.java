package ru.regorov.rrvs.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.repository.VoteRepository;
import ru.regorov.rrvs.to.VoteTo;
import ru.regorov.rrvs.util.VoteUtil;
import ru.regorov.rrvs.util.exceptions.EndVoteException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private static final LocalTime END_VOTING_TIME = LocalTime.of(11, 0);

    @Override
    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepo.findById(id).filter(vote -> vote.getUser().getId() == userId).orElse(null), id);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return voteRepo.getAll(userId);
    }

    @Override
    @Transactional
    public void save(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "vote must not be null");
        LocalDateTime now = LocalDateTime.now();
        Optional<Vote> optVote = voteRepo.findByUserIdAndDate(userId, now.toLocalDate());
        if (now.toLocalTime().isAfter(END_VOTING_TIME)) {
            throw new EndVoteException("Can not vote or change your choice after " + END_VOTING_TIME);
        }
        optVote.ifPresent(vote -> voteTo.setId(vote.getId()));
        voteRepo.save(constructVote(voteTo, now.toLocalDate(), userId));
    }

/*    @Override
    public void update(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "vote must not be null");
        voteRepo.save(constructVote(voteTo, userId));
    }*/

    private Vote constructVote(VoteTo voteTo, LocalDate date, int userId) {
        User user = userRepo.findById(userId).orElse(null);
        checkNotFoundWithId(user, userId);
        int restId = voteTo.getRestId();
        Restaurant restaurant = restRepo.findById(voteTo.getRestId()).orElse(null);
        checkNotFoundWithId(restaurant, restId);
        return VoteUtil.createFromTo(voteTo, date, restaurant, user);
    }

    @Override
    @Transactional
    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepo.delete(id, userId) != 0, id);
    }
}
