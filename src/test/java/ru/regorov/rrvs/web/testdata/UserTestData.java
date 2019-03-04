package ru.regorov.rrvs.web.testdata;

import ru.regorov.rrvs.model.Role;
import ru.regorov.rrvs.model.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int USER1_ID = 1;

    public static final User USER = new User(USER1_ID, "tony", "user", "1qaz2wsx", Role.ROLE_USER);
    public static final User ADMIN = new User(USER1_ID + 1, "regorov", "admin", "qwqwqw", Role.ROLE_USER, Role.ROLE_ADMIN);
    public static final User USER3 = new User(USER1_ID + 2, "nagorniy", "nag22", "qweasd", Role.ROLE_USER);
    public static final User USER4 = new User(USER1_ID + 3, "autotest", "userautotest", "zaq1xsw2", Role.ROLE_USER);
    public static final User USER5 = new User(USER1_ID + 4, "bortenev", "bva31", "bvabva", Role.ROLE_USER);
    public static final User USER6 = new User(USER1_ID + 5, "dubenskiy", "duboks", "qwe321", Role.ROLE_USER);
    public static final User USER7 = new User(USER1_ID + 6, "prebluda", "KL", "1qaz2wsx", Role.ROLE_USER);
    public static final User USER8 = new User(USER1_ID + 7, "old admin", "i_am_adm", "qwqwqw", Role.ROLE_ADMIN);

    public static final List<User> USERS = Arrays.asList(USER, ADMIN, USER3, USER4, USER5, USER6, USER7, USER8);

    public static User getCreated() {
        return new User(null, "new name", "newLogin", "superpass", Role.ROLE_USER);
    }

    public static User getUpdated() {
        return new User(USER1_ID, "montana", "updatedUser", "xsw2zaq1", Role.ROLE_USER);
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password").isEqualTo(expected);
    }
}