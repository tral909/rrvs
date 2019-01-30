package ru.regorov.rrvs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.regorov.rrvs.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.UserTestData.USER1;
import static ru.regorov.rrvs.UserTestData.USER1_ID;
import static ru.regorov.rrvs.UserTestData.USERS;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
//@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerIntegrationTest {
    private static final String REST_URL = "/users";

/*    @MockBean
    UserRepository userRepository;*/

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
        for (User user : USERS) {
            assertUser(result, user);
        }
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/" + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertUser(result, USER1);
    }

    @Test
    public void testCreate() throws Exception {
    }

    @Test
    public void testUpdate() throws Exception {
    }

    @Test
    public void testDelete() throws Exception {
    }

    private void assertUser(MvcResult result, User user) throws Exception {
        String responseTxt = result.getResponse().getContentAsString();
        assertThat(responseTxt, allOf(containsString(user.getName()),
                containsString(user.getLogin()),
                containsString(user.getPassword())));
    }
}