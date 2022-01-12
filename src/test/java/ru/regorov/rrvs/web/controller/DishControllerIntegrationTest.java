package ru.regorov.rrvs.web.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.repository.DishRepository;
import ru.regorov.rrvs.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.web.TestUtil.httpBasic;
import static ru.regorov.rrvs.web.controller.DishController.REST_URL;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH1;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH10;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH11;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH12;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH13;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH14;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH15;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH16;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH17;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH18;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH19;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH1_ID;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH2;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH20;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH21;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH22;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH23;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH24;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH25;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH3;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH4;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH5;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH6;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH7;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH8;
import static ru.regorov.rrvs.web.testdata.DishTestData.DISH9;
import static ru.regorov.rrvs.web.testdata.DishTestData.assertMatch;
import static ru.regorov.rrvs.web.testdata.DishTestData.getCreated;
import static ru.regorov.rrvs.web.testdata.DishTestData.getUpdated;
import static ru.regorov.rrvs.web.testdata.UserTestData.ADMIN;

public class DishControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private DishRepository dishRepo;

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        List<Dish> actual = JsonUtil.readValues(responseTxt, Dish.class);
        assertMatch(actual, dishRepo.getAll());
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/" + DISH1_ID)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        Dish actual = JsonUtil.readValue(responseTxt, Dish.class);
        assertMatch(actual, DISH1);
    }

    @Test
    public void testCreate() throws Exception {
        Dish created = getCreated();
        MvcResult result = mockMvc.perform(post(REST_URL)
                .with(httpBasic(ADMIN))
                .content(JsonUtil.writeValue(created))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        Dish actual = JsonUtil.readValue(responseTxt, Dish.class);
        created.setId(actual.getId());
        assertMatch(actual, created);
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = getUpdated();
        mockMvc.perform(put(REST_URL + "/" + updated.getId())
                .with(httpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishRepo.get(updated.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + DISH1_ID)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishRepo.getAll(), DISH2, DISH3, DISH4, DISH5,
                DISH6, DISH7, DISH8, DISH9, DISH10,
                DISH11, DISH12, DISH13, DISH14, DISH15,
                DISH16, DISH17, DISH18, DISH19, DISH20,
                DISH21, DISH22, DISH23, DISH24, DISH25);
    }
}