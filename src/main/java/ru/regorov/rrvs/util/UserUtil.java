package ru.regorov.rrvs.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.regorov.rrvs.model.Role;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.to.UserTo;

public class UserUtil {
    private UserUtil() {
    }

    public static User createNewFromTo(UserTo newUser, PasswordEncoder passwordEncoder) {
        String plainTxtPass = newUser.getPassword();
        String processedPass = StringUtils.isEmpty(plainTxtPass) ? plainTxtPass : passwordEncoder.encode(plainTxtPass);
        return new User(null, newUser.getName(), newUser.getLogin(), processedPass, Role.ROLE_USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setLogin(userTo.getLogin());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLogin(), user.getPassword());
    }
}