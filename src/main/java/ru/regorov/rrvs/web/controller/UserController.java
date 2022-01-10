package ru.regorov.rrvs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.regorov.rrvs.AuthorizedUser;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;
import ru.regorov.rrvs.to.UserTo;
import ru.regorov.rrvs.util.UserUtil;
import ru.regorov.rrvs.util.ValidationUtil;
import ru.regorov.rrvs.web.json.JsonUtil;

import javax.validation.Valid;
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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public ResponseEntity<String> create(@Valid @RequestBody UserTo user, BindingResult result) {
        log.info("create {}", user);
        if (result.hasErrors()) {
            return ValidationUtil.handleValidationErrors(result);
        }
        checkNew(user);
        User created = userRepository.create(createNewFromTo(user, passwordEncoder));
        return new ResponseEntity<>(JsonUtil.writeValue(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody UserTo user, BindingResult result, @PathVariable Integer id) {
        log.info("update {} with id {}", user, id);
        if (result.hasErrors()) {
            return ValidationUtil.handleValidationErrors(result);
        }
        assureIdConsistent(user, id);
        User curUser = userRepository.get(id);
        userRepository.update(UserUtil.updateFromTo(curUser, user));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
