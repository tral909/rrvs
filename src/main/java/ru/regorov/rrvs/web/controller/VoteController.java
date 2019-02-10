package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.repository.VoteRepository;
import ru.regorov.rrvs.to.VoteTo;
import ru.regorov.rrvs.web.SecurityUtil;

import java.util.List;

import static ru.regorov.rrvs.util.VoteUtil.asTo;

@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/votes";

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping
    public List<Vote> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll votes with userId={}", userId);
        return voteRepository.getAll(userId);
    }

    @GetMapping("/{id}")
    public VoteTo getVote(@PathVariable Integer id) {
        int userId = SecurityUtil.authUserId();
        log.info("getVote with id={} and userId={}", id, userId);
        return asTo(voteRepository.get(id, userId));
    }
}