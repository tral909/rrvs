package ru.regorov.rrvs.util;

import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(),
                restaurant.getName(),
                restaurant.getPhone(),
                restaurant.getAddress());
    }

    public static List<RestaurantTo> asTo(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtil::asTo)
                .collect(Collectors.toList());
    }
}
