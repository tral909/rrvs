package ru.regorov.rrvs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;
import ru.regorov.rrvs.to.UserTo;
import java.util.List;

import static ru.regorov.rrvs.util.UserUtil.*;
import static ru.regorov.rrvs.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/users";

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<UserTo> getAll() {
        log.info("getAll user");
        return allAsTo(userRepository.getAll());
    }

    @GetMapping("/{id}")
    public UserTo get(@PathVariable Integer id) {
        log.info("get user {}", id);
        return asTo(userRepository.get(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserTo> create(@RequestBody User user) {
        log.info("create user {}", user);
        UserTo created = asTo(userRepository.save(user));
        /*URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);*/
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable Integer id) {
        log.info("update user {} with id {}", user, id);
        assureIdConsistent(user, id);
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete user {}", id);
        userRepository.delete(id);
    }

}
