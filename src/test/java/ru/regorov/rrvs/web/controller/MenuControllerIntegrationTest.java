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
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.repository.MenuRepository;
import ru.regorov.rrvs.to.MenuTo;
import ru.regorov.rrvs.web.json.JsonUtil;
import ru.regorov.rrvs.web.testdata.DishTestData;
import ru.regorov.rrvs.web.testdata.MenuToTestData;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.util.MenuUtil.asTo;
import static ru.regorov.rrvs.web.TestUtil.httpBasic;
import static ru.regorov.rrvs.web.controller.MenuController.REST_URL;
import static ru.regorov.rrvs.web.testdata.MenuToTestData.*;
import static ru.regorov.rrvs.web.testdata.UserTestData.ADMIN;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@Transactional
public class MenuControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MenuRepository menuRepo;

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        List<MenuTo> actual = JsonUtil.readValues(responseTxt, MenuTo.class);
        assertMatch(actual, asTo(menuRepo.getAll()));
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/" + MENU_TO1_ID)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        Menu actual = JsonUtil.readValue(responseTxt, Menu.class);
        Menu expected = menuRepo.get(MENU_TO1_ID);
        MenuToTestData.assertMatch(actual, expected);
        DishTestData.assertMatchIgnoringOrder(actual.getDishes(), expected.getDishes());
    }

    @Test
    public void testCreate() throws Exception {
        MenuTo created = getCreated();
        MvcResult result = mockMvc.perform(post(REST_URL)
                .with(httpBasic(ADMIN))
                .content(JsonUtil.writeValue(created))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        MenuTo actual = JsonUtil.readValue(responseTxt, MenuTo.class);
        created.setId(actual.getId());
        assertMatch(actual, created);
    }

    @Test
    public void testAppendDishToMenu() throws Exception {
        mockMvc.perform(post(MenuController.REST_URL + "/" +
                            MENU_TO1_ID +
                            DishController.REST_URL +
                            "/" + 3)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        Menu actualMenu = menuRepo.get(MENU_TO1_ID);
        DishTestData.assertMatchIgnoringOrder(actualMenu.getDishes(),
                DishTestData.DISH1, DishTestData.DISH3,
                DishTestData.DISH8, DishTestData.DISH15,
                DishTestData.DISH20, DishTestData.DISH21);
    }

    @Test
    public void testDeleteDishFromMenu() throws Exception {
        mockMvc.perform(delete(MenuController.REST_URL + "/" +
                            MENU_TO1_ID + DishController.REST_URL +
                            "/" + 1)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        Menu afterMenu = menuRepo.get(MENU_TO1_ID);
        DishTestData.assertMatchIgnoringOrder(afterMenu.getDishes(),
                DishTestData.DISH8, DishTestData.DISH15,
                DishTestData.DISH20, DishTestData.DISH21);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + MENU_TO1_ID)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(asTo(menuRepo.getAll()), MENU_TO2, MENU_TO3, MENU_TO4, MENU_TO5);
    }
}