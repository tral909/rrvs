package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.repository.VoteRepository;
import ru.regorov.rrvs.to.VoteTo;
import ru.regorov.rrvs.web.SecurityUtil;

import java.util.List;

import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;
import static ru.regorov.rrvs.util.VoteUtil.asTo;

//TODO сделать тест на этот контроллер
//TODO добавить в доку
@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/votes";

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping
    public List<VoteTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll votes with userId={}", userId);
        return asTo(voteRepository.getAll(userId));
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable Integer id) {
        int userId = SecurityUtil.authUserId();
        log.info("get vote with id={} and userId={}", id, userId);
        return asTo(voteRepository.get(id, userId));
    }

    //TODO before 11:00 and after condition - need service with logic
    //TODO не принимать у клиента вермя, создавать самому в UTC и сохранять в базу
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteTo create(@RequestBody VoteTo voteTo) {
        int userId = SecurityUtil.authUserId();
        log.info("create vote {} with userId={}", voteTo, userId);
        return asTo(voteRepository.create(voteTo, userId));
    }

    //TODO возможно этот метод не нужен (можем только создавать и перезатирать голос
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody VoteTo voteTo) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(voteTo, id);
        log.info("update vote {} with userId={}", voteTo, userId);
        voteRepository.update(voteTo, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete vote with id={} and userId={}", id, userId);
        voteRepository.delete(id, userId);
    }
}