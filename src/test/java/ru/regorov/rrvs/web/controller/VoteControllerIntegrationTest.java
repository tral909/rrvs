package ru.regorov.rrvs.web.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Base64Utils;
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
import static ru.regorov.rrvs.web.TestUtil.httpBasic;
import static ru.regorov.rrvs.web.controller.VoteController.REST_URL;
import static ru.regorov.rrvs.web.testdata.UserTestData.USER;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoteControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepo;

    @LocalServerPort
    private int srvPort;

    @Value("#{T(java.time.LocalTime).parse('${end.voting.time}')}")
    private LocalTime END_VOTING_TIME;

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();

        int id1 = JsonPath.parse(responseTxt).read("$.[0].id");
        int restId1 = JsonPath.parse(responseTxt).read("$.[0].restaurant_id");
        assertThat(id1, equalTo(1));
        assertThat(restId1, equalTo(1));

        int id2 = JsonPath.parse(responseTxt).read("$.[1].id");
        int restId2 = JsonPath.parse(responseTxt).read("$.[1].restaurant_id");
        assertThat(id2, equalTo(2));
        assertThat(restId2, equalTo(3));
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/{id}", 1)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseTxt = result.getResponse().getContentAsString();

        int id1 = JsonPath.parse(responseTxt).read("$.id");
        int restId1 = JsonPath.parse(responseTxt).read("$.restaurant_id");
        assertThat(id1, equalTo(1));
        assertThat(restId1, equalTo(1));
    }

    @Test
    public void testSave() throws Exception {
        int savedRestId = 4;
        int userId = 1;
        String voteTo = "{\"restaurant_id\": " + savedRestId + "}";
        LocalDateTime now = LocalDateTime.now();
        boolean isEndVoting = now.toLocalTime().isAfter(END_VOTING_TIME);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new NoErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Authorization", "Basic " + new String(Base64Utils.encodeUrlSafe((USER.getLogin() + ":" + USER.getPassword()).getBytes())));
        HttpEntity<String> requestEntity = new HttpEntity<>(voteTo, headers);
        ResponseEntity<String> result = restTemplate.exchange(
                "http://localhost:" + srvPort + REST_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);
        if (isEndVoting) {
            assertThat(result.getStatusCode(), equalTo(HttpStatus.CONFLICT));
            if (result.hasBody()) {
                String responseTxt = result.getBody();
                String errMsg = JsonPath.parse(responseTxt).read("$.details");
                assertThat(errMsg, equalTo("Can not vote or change your choice after 11:00"));
            }
        } else {
            assertThat(result.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
            List<Vote> actVotes = voteRepo.getAll(userId);
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
        int userId = 1;
        mockMvc.perform(delete(REST_URL + "/{id}", 1)
                .with(httpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
        // need this, because restTemplate in testSave method doesnt work with @transactional test
        // (testDelete fails with 1 expected and 2 actual votes)
        int expectedVotesCount = 1;
        if (LocalDateTime.now().toLocalTime().isBefore(END_VOTING_TIME)) {
            expectedVotesCount = 2;
        }
        assertThat(voteRepo.getAll(userId).size(), equalTo(expectedVotesCount));
    }
}