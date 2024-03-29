package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.repository.RestaurantRepository;
import ru.regorov.rrvs.repository.UserRepository;
import ru.regorov.rrvs.repository.VoteRepository;
import ru.regorov.rrvs.to.VoteTo;
import ru.regorov.rrvs.util.VoteUtil;
import ru.regorov.rrvs.util.exceptions.EndVoteException;
import ru.regorov.rrvs.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.regorov.rrvs.util.VoteUtil.asTo;

@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/votes";

    @Value("#{T(java.time.LocalTime).parse('${end.voting.time}')}")
    private LocalTime END_VOTING_TIME;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public List<VoteTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll with userId={}", userId);
        return asTo(voteRepository.getAll(userId));
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable Integer id) {
        int userId = SecurityUtil.authUserId();
        log.info("get with id={} and userId={}", id, userId);
        return asTo(voteRepository.get(id, userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestBody VoteTo voteTo) {
        int userId = SecurityUtil.authUserId();
        log.info("save {} with userId={}", voteTo, userId);
        LocalDateTime now = LocalDateTime.now();
        Optional<Vote> optVote = voteRepository.findByUserIdAndDate(userId, now.toLocalDate());
        if (now.toLocalTime().isAfter(END_VOTING_TIME)) {
            throw new EndVoteException("Can not vote or change your choice after " + END_VOTING_TIME);
        }
        optVote.ifPresent(vote -> voteTo.setId(vote.getId()));
        voteRepository.save(constructVote(voteTo, now.toLocalDate(), userId));
    }

    private Vote constructVote(VoteTo voteTo, LocalDate date, int userId) {
        User user = userRepository.getRef(userId);
        int restId = voteTo.getRestId();
        Restaurant restaurant = restaurantRepository.getRef(restId);
        return VoteUtil.createFromTo(voteTo, date, restaurant, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete with id={} and userId={}", id, userId);
        voteRepository.delete(id, userId);
    }
}