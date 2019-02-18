package ru.regorov.rrvs.web.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.repository.VoteRepository;
import ru.regorov.rrvs.util.VoteUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.regorov.rrvs.web.controller.VoteController.REST_URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
@Transactional
public class VoteControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VoteRepository voteRepo;

    @LocalServerPort
    int srvPort;

    @Value("#{T(java.time.LocalTime).parse('${end.voting.time}')}")
    private LocalTime END_VOTING_TIME;

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

    @Test
    public void testSave() throws Exception {
        int savedRestId = 4;
        String voteTo = "{\"restId\": " + savedRestId + "}";
        LocalDateTime now = LocalDateTime.now();
        boolean isEndVoting = now.toLocalTime().isAfter(END_VOTING_TIME);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new NoErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<>(voteTo, headers);
        ResponseEntity<String> result = restTemplate.exchange(
                "http://localhost:" + srvPort + REST_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);
        if (isEndVoting) {
            assertThat(result.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
            if (result.hasBody()) {
                String responseTxt = result.getBody().toString();
                String errMsg = JsonPath.parse(responseTxt).read("$.message");
                assertThat(errMsg, equalTo("Can not vote or change your choice after 11:00"));
            }
        } else {
            assertThat(result.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
            List<Vote> actVotes = voteRepo.getAll(1);
            assertThat(actVotes.size(), equalTo(3));
            int savedVoteRestId = VoteUtil.asTo(actVotes.get(2)).getRestId();
            assertThat(savedVoteRestId, equalTo(savedRestId));
        }
//        ------------ MAY BE I CAN SET CUSTOM ERROR HANDLER FOR MOCKMVC -------------
//        boolean isEndVoting = now.toLocalTime().isAfter(endVoting);
//        MvcResult result = mockMvc.perform(post(REST_URL)
//                    .content(JsonUtil.writeValue(voteTo))
//                    .contentType(MediaType.APPLICATION_JSON_UTF8))
//                    .andDo(print())
//                    .andExpect(isEndVoting ? status().isInternalServerError() : status().isOk())
//                    .andReturn();
//        if (isEndVoting) {
//            String responseTxt = result.getResponse().getContentAsString();
//            String errorMsg = JsonPath.parse(responseTxt).read("$.message");
//            assertThat(errorMsg, equalTo("Can not vote or change your choice after 11:00"));
//        }
    }

    public static class NoErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // Do nothing, need to check answer 500 error for test purpose
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