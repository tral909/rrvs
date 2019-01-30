package ru.regorov.rrvs.web;

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
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.repository.UserRepository;
import ru.regorov.rrvs.to.UserTo;
import ru.regorov.rrvs.util.UserUtil;
import ru.regorov.rrvs.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.UserTestData.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@Transactional
//@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerIntegrationTest {
    private static final String REST_URL = "/users";

/*    @MockBean
    UserRepository userRepository;*/
    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {
//        when(userRepository.getAll()).thenReturn(Collections.singletonList(new User(2, "", "", "")));
        MvcResult result = mockMvc.perform(get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        /*for (User user : USERS) {
            assertUser(result, user);
        }*/
        String responseTxt = result.getResponse().getContentAsString();
        List<User> actual = UserUtil.allAsModel(JsonUtil.readValues(responseTxt, UserTo.class));
        assertMatch(actual, USERS);
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/" + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //assertUser(result, USER1);
        String responseTxt = result.getResponse().getContentAsString();
        User actual = UserUtil.asModel(JsonUtil.readValue(responseTxt, UserTo.class));
        assertMatch(actual, USER1);
    }

    @Test
    public void testCreate() throws Exception {
        User expected = getCreated();
        UserTo createdTo = UserUtil.createNewToFrom(expected);
        MvcResult result = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();
        User actual = UserUtil.asModel(JsonUtil.readValue(responseTxt, UserTo.class));
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }

    @Test
    public void testUpdate() throws Exception {
        User expected = getUpdated();
        UserTo updatedTo = UserUtil.createNewToFrom(expected);
        mockMvc.perform(put(REST_URL + "/" + expected.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userRepository.get(expected.getId()), expected);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userRepository.getAll(), USER2, USER3, USER4, USER5, USER6, USER7, USER8);
    }

/*    private void assertUser(MvcResult result, User user) throws Exception {
        String responseTxt = result.getResponse().getContentAsString();
        assertThat(responseTxt, allOf(containsString(user.getName()),
                containsString(user.getLogin()),
                containsString(user.getPassword())));
    }*/
}