package ru.regorov.rrvs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;
import ru.regorov.rrvs.to.UserTo;
import ru.regorov.rrvs.util.UserUtil;

import java.util.List;

import static ru.regorov.rrvs.util.UserUtil.createNewFromTo;
import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/users";

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        log.info("getAll users");
        return userRepository.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        log.info("get user {}", id);
        return userRepository.get(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@RequestBody UserTo user) {
        log.info("create user {}", user);
        return userRepository.create(createNewFromTo(user));
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo user, @PathVariable Integer id) {
        log.info("update user {} with id {}", user, id);
        assureIdConsistent(user, id);
        User curUser = userRepository.get(id);
        userRepository.update(UserUtil.updateFromTo(curUser, user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete user {}", id);
        userRepository.delete(id);
    }

}
