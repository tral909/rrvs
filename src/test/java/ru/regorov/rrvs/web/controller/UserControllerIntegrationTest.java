package ru.regorov.rrvs.web.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;
import ru.regorov.rrvs.to.UserTo;
import ru.regorov.rrvs.util.UserUtil;
import ru.regorov.rrvs.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.web.TestUtil.httpBasic;
import static ru.regorov.rrvs.web.controller.UserController.REST_URL;
import static ru.regorov.rrvs.web.testdata.UserTestData.ADMIN;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER1_ID;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER3;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER4;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER5;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER6;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER7;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER8;
import static ru.regorov.rrvs.web.testdata.UserTestData.USERS;
import static ru.regorov.rrvs.web.testdata.UserTestData.assertMatch;
import static ru.regorov.rrvs.web.testdata.UserTestData.getCreated;
import static ru.regorov.rrvs.web.testdata.UserTestData.getUpdated;

public class UserControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        List<User> actual = JsonUtil.readValues(responseTxt, User.class);
        assertMatch(actual, USERS);
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/" + USER1_ID)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        User actual = JsonUtil.readValue(responseTxt, User.class);
        assertMatch(actual, USER);
    }

    @Test
    public void testCreate() throws Exception {
        User expected = getCreated();
        UserTo createdTo = UserUtil.asTo(expected);
        MvcResult result = mockMvc.perform(post(REST_URL)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        User actual = JsonUtil.readValue(responseTxt, User.class);
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }

    @Test
    public void testUpdate() throws Exception {
        User expected = getUpdated();
        UserTo updatedTo = UserUtil.asTo(expected);
        mockMvc.perform(put(REST_URL + "/" + expected.getId())
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userRepository.get(expected.getId()), expected);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + USER1_ID)
                .with(httpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userRepository.getAll(), ADMIN, USER3, USER4, USER5, USER6, USER7, USER8);
    }
}