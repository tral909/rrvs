package ru.regorov.rrvs.web.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.repository.RestaurantRepository;
import ru.regorov.rrvs.web.json.JsonUtil;
import ru.regorov.rrvs.web.testdata.DishTestData;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.web.TestUtil.httpBasic;
import static ru.regorov.rrvs.web.controller.RestaurantController.RESTAURANTS_URL;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.RESTAURANT1;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.RESTAURANT2;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.RESTAURANT3;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.RESTAURANT4;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.RESTAURANT5;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.RESTAURANT_ID;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.assertMatch;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.getCreated;
import static ru.regorov.rrvs.web.testdata.RestaurantTestData.getUpdated;
import static ru.regorov.rrvs.web.testdata.UserTestData.ADMIN;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER;

public class RestaurantControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(get(RESTAURANTS_URL)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        List<Restaurant> actual = JsonUtil.readValues(responseTxt, Restaurant.class);
        assertMatch(actual, restaurantRepo.getAll());
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(RESTAURANTS_URL + "/{id}", RESTAURANT_ID)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        Restaurant actual = JsonUtil.readValue(responseTxt, Restaurant.class);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    public void testGetMenusByRestaurantId() throws Exception {
        MvcResult result = mockMvc.perform(get(RESTAURANTS_URL + "/{id}/menus", RESTAURANT_ID)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        int id = JsonPath.parse(responseTxt).read("$.[0].id");
        String date = JsonPath.parse(responseTxt).read("$.[0].date");
        assertThat(id, equalTo(1));
        assertThat(date, equalTo("2019-01-26"));
    }

    @Test
    public void getMenusByRestaurantIdAndDate() throws Exception {
        MvcResult result = mockMvc.perform(get(RESTAURANTS_URL + "/{id}/menus/filter?date=2019-01-26", RESTAURANT_ID)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        int id = JsonPath.parse(responseTxt).read("$.[0].id");
        String date = JsonPath.parse(responseTxt).read("$.[0].date");
        int dishesLength = JsonPath.parse(responseTxt).read("$.[0].dishes.length()");
        assertThat(id, equalTo(1));
        assertThat(date, equalTo("2019-01-26"));
        assertThat(dishesLength, equalTo(5));
        String dishes = JsonPath.parse(responseTxt).read("$.[0].dishes").toString();
        List<Dish> actualDishes = JsonUtil.readValues(dishes, Dish.class);
        DishTestData.assertMatchIgnoringOrder(actualDishes, DishTestData.DISH1,
                DishTestData.DISH8, DishTestData.DISH15,
                DishTestData.DISH20, DishTestData.DISH21);
    }

    @Test
    public void getDishesByRestaurantIdAndMenuId() throws Exception {
        MvcResult result = mockMvc.perform(get(RESTAURANTS_URL + "/{id}/menus/{id}", RESTAURANT_ID, 1)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        List<Dish> actual = JsonUtil.readValues(responseTxt, Dish.class);
        DishTestData.assertMatch(actual, DishTestData.DISH1,
                DishTestData.DISH8, DishTestData.DISH15,
                DishTestData.DISH20, DishTestData.DISH21);
    }

    @Test
    public void testCreate() throws Exception {
        Restaurant created = getCreated();
        MvcResult result = mockMvc.perform(post(RESTAURANTS_URL)
                .with(httpBasic(ADMIN))
                .content(JsonUtil.writeValue(created))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        Restaurant actual = JsonUtil.readValue(responseTxt, Restaurant.class);
        created.setId(actual.getId());
        assertMatch(actual, created);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = getUpdated();
        mockMvc.perform(put(RESTAURANTS_URL + "/{id}", updated.getId())
                .with(httpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantRepo.get(updated.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(RESTAURANTS_URL + "/{id}", RESTAURANT_ID)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantRepo.getAll(), RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5);
    }
}
