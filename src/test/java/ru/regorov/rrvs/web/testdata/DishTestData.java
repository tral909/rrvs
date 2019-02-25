package ru.regorov.rrvs.web.testdata;

import ru.regorov.rrvs.model.Dish;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final int DISH1_ID = 1;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Картофель жареный", 35);
    public static final Dish DISH2 = new Dish(DISH1_ID + 1, "Рис", 30);
    public static final Dish DISH3 = new Dish(DISH1_ID + 2, "Гречка", 30);
    public static final Dish DISH4 = new Dish(DISH1_ID + 3, "Макароны", 25);
    public static final Dish DISH5 = new Dish(DISH1_ID + 4, "Плов", 55);
    public static final Dish DISH6 = new Dish(DISH1_ID + 5, "Сок яблочный", 45);
    public static final Dish DISH7 = new Dish(DISH1_ID + 6, "Сок апельсиновый", 50);
    public static final Dish DISH8 = new Dish(DISH1_ID + 7, "Чай", 30);
    public static final Dish DISH9 = new Dish(DISH1_ID + 8, "Кофе", 40);
    public static final Dish DISH10 = new Dish(DISH1_ID + 9, "Riga Balzams", 210);
    public static final Dish DISH11 = new Dish(DISH1_ID + 10, "Котлета из говядины", 110);
    public static final Dish DISH12 = new Dish(DISH1_ID + 11, "Курица в панировке", 120);
    public static final Dish DISH13 = new Dish(DISH1_ID + 12, "Стейк из лосося", 300);
    public static final Dish DISH14 = new Dish(DISH1_ID + 13, "Свиные медальоны", 130);
    public static final Dish DISH15 = new Dish(DISH1_ID + 14, "Пельмени", 90);
    public static final Dish DISH16 = new Dish(DISH1_ID + 15, "Рассольник", 55);
    public static final Dish DISH17 = new Dish(DISH1_ID + 16, "Куриная лапша", 50);
    public static final Dish DISH18 = new Dish(DISH1_ID + 17, "Борщ", 60);
    public static final Dish DISH19 = new Dish(DISH1_ID + 18, "Солянка мясная", 110);
    public static final Dish DISH20 = new Dish(DISH1_ID + 19, "Суп грибной", 60);
    public static final Dish DISH21 = new Dish(DISH1_ID + 20, "Салат овощной", 45);
    public static final Dish DISH22 = new Dish(DISH1_ID + 21, "Оливье", 55);
    public static final Dish DISH23 = new Dish(DISH1_ID + 22, "Капуста с морковкой", 25);
    public static final Dish DISH24 = new Dish(DISH1_ID + 23, "Винигрет", 30);
    public static final Dish DISH25 = new Dish(DISH1_ID + 24, "Крабовый салат", 60);

    public static final List<Dish> DISHES = Arrays.asList(
            DISH1, DISH2, DISH3, DISH4, DISH5,
            DISH6, DISH7, DISH8, DISH9, DISH10,
            DISH11, DISH12, DISH13, DISH14, DISH15,
            DISH16, DISH17, DISH18, DISH19, DISH20,
            DISH21, DISH22, DISH23, DISH24, DISH25
    );

    public static Dish getCreated() {
        return new Dish(null, "TASTY MEAL", 777);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Картофель по-деревенски", 45);
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menus");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menus").isEqualTo(expected);
    }

    public static void assertMatchIgnoringOrder(Iterable<Dish> actual, Dish... expected) {
        assertMatchIgnoringOrder(actual, Arrays.asList(expected));
    }

    public static void assertMatchIgnoringOrder(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menus").containsExactlyInAnyOrderElementsOf(expected);
    }
}
