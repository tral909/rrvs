package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.AuthorizedUser;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;
import ru.regorov.rrvs.to.UserTo;
import ru.regorov.rrvs.util.UserUtil;

import java.util.List;

import static ru.regorov.rrvs.util.UserUtil.createNewFromTo;
import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;
import static ru.regorov.rrvs.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/users";

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        log.info("getAll");
        return userRepository.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@RequestBody UserTo user) {
        log.info("create {}", user);
        checkNew(user);
        return userRepository.create(createNewFromTo(user));
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo user, @PathVariable Integer id) {
        log.info("update {} with id {}", user, id);
        assureIdConsistent(user, id);
        User curUser = userRepository.get(id);
        userRepository.update(UserUtil.updateFromTo(curUser, user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete {}", id);
        userRepository.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
