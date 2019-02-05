package ru.regorov.rrvs.testdata;

import ru.regorov.rrvs.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int RESTNT1_ID = 1;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTNT1_ID,
            "CIN CIN",
            "+7(495)2236547",
            "г. Москва, ул. Мирская, д. 12");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTNT1_ID + 1,
            "Старый город",
            "+7(495)1245298",
            "г. Москва, Старый Петровско-Разумовский проезд, д. 21/3");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTNT1_ID + 2,
            "Obed.ru",
            "+7(495)7778878",
            "г. Москва, уд. Строителей, д. 34");
    public static final Restaurant RESTAURANT4 = new Restaurant(RESTNT1_ID + 3,
            "Клевер",
            "+7(4922)389104",
            "г. Владимир, ул. Красная, д. 15");
    public static final Restaurant RESTAURANT5 = new Restaurant(RESTNT1_ID + 4,
            "Golden cock",
            "+8(0456)25567890",
            "Riga, Skarnu iela, 22");

    public static final List<Restaurant> USERS = Arrays.asList(
            RESTAURANT1,
            RESTAURANT2,
            RESTAURANT3,
            RESTAURANT4,
            RESTAURANT5);

    public static Restaurant getCreated() {
        return new Restaurant(null, "new restarau", "7777777", "RESTfulRestarau");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTNT1_ID, "2 CIN", "+7(495)567652", "г. Санкт-Петербург, ул. Артиллерийская, д. 2");
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        //assertThat(actual).isEqualToComparingFieldByField(expected);
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menus");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        //assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
        assertThat(actual).usingElementComparatorIgnoringFields("menus").isEqualTo(expected);
    }
}