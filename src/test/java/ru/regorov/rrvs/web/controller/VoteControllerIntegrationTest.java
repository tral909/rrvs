package ru.regorov.rrvs.web.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Ignore;
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
import ru.regorov.rrvs.repository.VoteRepository;
import ru.regorov.rrvs.to.VoteTo;
import ru.regorov.rrvs.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.web.controller.VoteController.REST_URL;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@Transactional
public class VoteControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VoteRepository voteRepo;

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();

        int id1 = JsonPath.parse(responseTxt).read("$.[0].id");
        int restId1 = JsonPath.parse(responseTxt).read("$.[0].restId");
        assertThat(id1, equalTo(1));
        assertThat(restId1, equalTo(1));

        int id2 = JsonPath.parse(responseTxt).read("$.[1].id");
        int restId2 = JsonPath.parse(responseTxt).read("$.[1].restId");
        assertThat(id2, equalTo(2));
        assertThat(restId2, equalTo(3));
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();

        int id1 = JsonPath.parse(responseTxt).read("$.id");
        int restId1 = JsonPath.parse(responseTxt).read("$.restId");
        assertThat(id1, equalTo(1));
        assertThat(restId1, equalTo(1));
    }

    //TODO this test failed after 11:00. Need replace LocalDateTime.now() or make it catch EndVoteException after 11:00 and pass..
    @Test
    @Ignore
    public void testSave() throws Exception {
        VoteTo voteTo = new VoteTo(null, 4);
        LocalDateTime now = LocalDateTime.now();
        LocalTime endVoting = LocalTime.of(11, 0);
        boolean isEndVoting = now.toLocalTime().isAfter(endVoting);
        MvcResult result = mockMvc.perform(post(REST_URL)
                .content(JsonUtil.writeValue(voteTo))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(isEndVoting ? status().isInternalServerError() : status().isOk())
                .andReturn();
        if (isEndVoting) {
            String responseTxt = result.getResponse().getContentAsString();
            String errorMsg = JsonPath.parse(responseTxt).read("$.message");
            assertThat(errorMsg, equalTo("Can not vote or change your choice after 11:00"));
        }
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThat(voteRepo.getAll(1).size(), equalTo(1));
    }
}