package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.domain.Difficulty;
import com.rozplochowski.studio.shared.application.in.PagedResponse;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ClassManagementApiIntegrationTest {

    @Container
    @ServiceConnection
    static final MySQLContainer MYSQL = new MySQLContainer("mysql:9.7");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory.runInTransaction(work ->
                work.createQuery("delete from DanceClassEntity").executeUpdate()
        );
    }

    @Test
    void shouldCreateAndPersistDanceClass() throws Exception {
        var request = new CreateDanceClassRequest("Boogie Woogie", "Advanced class for experienced dancers", Difficulty.HARD);
        ResultMatcher[] matchers = {
                jsonPath("$.id").isNumber(),
                jsonPath("$.activity").value(request.activity()),
                jsonPath("$.description").value(request.description()),
                jsonPath("$.difficulty").value(request.difficulty().name())
        };

        var result = mockMvc.perform(post("/dance-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpectAll(matchers)
                .andReturn();

        var location = result.getResponse().getHeader("Location");
        assertNotNull(location);

        mockMvc.perform(get(location).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(matchers);
    }


    @Test
    void shouldUpdateDanceClass() throws Exception {
        var request = new CreateDanceClassRequest("Boogie Woogie", "Advanced class for experienced dancers", Difficulty.HARD);
        var id = createDanceClass(request).id();

        var updateRequest = new UpdateDanceClassRequest(id, "Boogie Woogie Masters", "Advanced class", Difficulty.PRO);
        ResultMatcher[] matchers = {
                status().isOk(),
                jsonPath("$.id").value(id),
                jsonPath("$.activity").value(updateRequest.activity()),
                jsonPath("$.description").value(updateRequest.description()),
                jsonPath("$.difficulty").value(updateRequest.difficulty().name())
        };
        mockMvc.perform(put("/dance-classes/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateRequest)))
                .andExpectAll(matchers);

        mockMvc.perform(get("/dance-classes/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(matchers);
    }

    @Test
    void shouldRetrieveDanceClassById() throws Exception {
        var request = new CreateDanceClassRequest("Boogie Woogie", "Advanced class for experienced dancers", Difficulty.HARD);
        var location = mockMvc.perform(post("/dance-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andReturn().getResponse().getHeader("Location");

        assertNotNull(location);
        mockMvc.perform(get(location)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNumber(),
                        jsonPath("$.activity").value(request.activity()),
                        jsonPath("$.description").value(request.description()),
                        jsonPath("$.difficulty").value(request.difficulty().name())
                );
    }

    @Test
    void shouldGetAllDanceClasses() throws Exception {
        var request1 = new CreateDanceClassRequest("Boogie Woogie", "Advanced class", Difficulty.HARD);
        var request2 = new CreateDanceClassRequest("Lindy Hop", "Starter", Difficulty.EASY);
        var request3 = new CreateDanceClassRequest("Solo Jazz", "Open class", Difficulty.MEDIUM);
        var requests = List.of(request1, request2, request3);

        List<DanceClassResponse> responses = new ArrayList<>();
        for (var request : requests) {
            responses.add(createDanceClass(request));
        }

        var expected = new PagedResponse<>(responses, 0, 10, 1);
        mockMvc.perform(get("/dance-classes")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("pageNumber", "0")
                        .queryParam("pageSize", "10"))
                .andExpectAll(
                        status().isOk(),
                        content().json(mapper.writeValueAsString(expected))
                );
    }

    @ParameterizedTest
    @MethodSource("invalidCreateDanceClassRequests")
    void shouldFailToCreateDanceClassWithInvalidArguments(CreateDanceClassRequest request) throws Exception {

        mockMvc.perform(post("/dance-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private static Stream<CreateDanceClassRequest> invalidCreateDanceClassRequests() {
        return Stream.of(
                new CreateDanceClassRequest(null, "Open class", Difficulty.MEDIUM),
                new CreateDanceClassRequest("", "Open class", Difficulty.MEDIUM),
                new CreateDanceClassRequest("   ", "Open class", Difficulty.MEDIUM),
                new CreateDanceClassRequest("Solo Jazz", null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateDanceClassRequests")
    void shouldFailToUpdateDanceClassWithInvalidArguments(String activity, Difficulty difficulty) throws Exception {
        var createRequest = new CreateDanceClassRequest("Solo Jazz", null, Difficulty.HARD);
        var id = createDanceClass(createRequest).id();

        var updateRequest = new UpdateDanceClassRequest(id, activity, "description", difficulty);
        mockMvc.perform(put("/dance-classes/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/dance-classes/" + id))
                .andExpectAll(status().isOk(),
                        content().json(
                                mapper.writeValueAsString(new DanceClassResponse(id, "Solo Jazz", null, Difficulty.HARD))
                        ));
    }

    private static Stream<Arguments> invalidUpdateDanceClassRequests() {
        return Stream.of(
                Arguments.of("", Difficulty.EASY),
                Arguments.of("  ", Difficulty.EASY),
                Arguments.of(null, Difficulty.EASY),
                Arguments.of("New name", null)
        );
    }

    private DanceClassResponse createDanceClass(CreateDanceClassRequest request) throws Exception {
        var content = mockMvc.perform(post("/dance-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readValue(content, DanceClassResponse.class);
    }

}
