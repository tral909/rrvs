package ru.regorov.rrvs.util;

import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.to.MenuTo;

import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {
    private MenuUtil() {
    }

    public static MenuTo asTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDate(), menu.getRestaurant().getId());
    }

    public static List<MenuTo> asTo(List<Menu> menus) {
        return menus.stream()
                .map(MenuUtil::asTo)
                .collect(Collectors.toList());
    }

    public static Menu createFromTo(MenuTo menuTo, Restaurant restaurant) {
        Menu menu = new Menu(menuTo.getId(), menuTo.getDate());
        menu.setRestaurant(restaurant);
        return menu;
    }
}
