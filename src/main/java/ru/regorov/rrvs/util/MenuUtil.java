package ru.regorov.rrvs.util;

import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.to.MenuTo;

import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {
    private MenuUtil() {
    }

    public static MenuTo asTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDate());
    }

    public static List<MenuTo> asTo(List<Menu> menus) {
        return menus.stream()
                .map(MenuUtil::asTo)
                .collect(Collectors.toList());
    }
}
