package ru.regorov.rrvs.util;

import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.to.UserTo;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {
    private UserUtil() {
    }

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getLogin(), newUser.getPassword());
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLogin(), user.getPassword());
    }

    public static List<UserTo> allAsTo(List<User> users) {
        return users.stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }
}