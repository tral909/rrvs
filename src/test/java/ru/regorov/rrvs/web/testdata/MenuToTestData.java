package ru.regorov.rrvs.web.testdata;

import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.to.MenuTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuToTestData {
    public static final int MENU_TO_ID = 1;
    public static final int RESTAURANT_ID = 1;

    public static final String DATE = "2019-01-26";
    public static final MenuTo MENU_TO1 = new MenuTo(MENU_TO_ID, LocalDate.parse(DATE), RESTAURANT_ID);
    public static final MenuTo MENU_TO2 = new MenuTo(MENU_TO_ID + 1, LocalDate.parse(DATE), RESTAURANT_ID + 1);
    public static final MenuTo MENU_TO3 = new MenuTo(MENU_TO_ID + 2, LocalDate.parse(DATE), RESTAURANT_ID + 2);
    public static final MenuTo MENU_TO4 = new MenuTo(MENU_TO_ID + 3, LocalDate.parse(DATE), RESTAURANT_ID + 3);
    public static final MenuTo MENU_TO5 = new MenuTo(MENU_TO_ID + 4, LocalDate.parse(DATE), RESTAURANT_ID + 4);

    public static final List<MenuTo> MENUS = Arrays.asList(MENU_TO1, MENU_TO2, MENU_TO3, MENU_TO3, MENU_TO4, MENU_TO5);

    public static MenuTo getCreated() {
        return new MenuTo(null, LocalDate.parse("2019-02-23"), 1);
    }

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "dishes");
    }

    public static void assertMatch(MenuTo actual, MenuTo expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<MenuTo> actual, MenuTo... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<MenuTo> actual, Iterable<MenuTo> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
