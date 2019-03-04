package ru.regorov.rrvs.web;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.regorov.rrvs.model.User;

public class TestUtil {
    private TestUtil() {
    }

    public static RequestPostProcessor httpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getLogin(), user.getPassword());
    }
}
