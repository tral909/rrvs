package ru.regorov.rrvs.util;

import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.to.DishTo;

import java.util.List;
import java.util.stream.Collectors;

public class DishUtil {
    private DishUtil() {
    }

    public static DishTo asTo(Dish dish){
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }

    public static List<DishTo> asTo(List<Dish> dishes) {
        return dishes.stream()
                .map(DishUtil::asTo)
                .collect(Collectors.toList());
    }
}
