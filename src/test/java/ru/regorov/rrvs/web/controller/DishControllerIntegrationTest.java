package ru.regorov.rrvs.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.regorov.rrvs.model.Dish;
import ru.regorov.rrvs.repository.DishRepository;
import ru.regorov.rrvs.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.web.TestUtil.httpBasic;
import static ru.regorov.rrvs.web.controller.DishController.REST_URL;
import static ru.regorov.rrvs.web.testdata.DishTestData.*;
import static ru.regorov.rrvs.web.testdata.UserTestData.ADMIN;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DishControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DishRepository dishRepo;

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