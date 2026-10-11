package com.rozplochowski.studio.classrooms.adapter.in.web;

import com.jayway.jsonpath.JsonPath;
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
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import tools.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ClassroomApiIntegrationTest {

    @Container
    @ServiceConnection
    static final MySQLContainer MYSQL = new MySQLContainer("mysql:9.7");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void shouldCreateAndPersistClassroom() throws Exception {
        var result = mockMvc.perform(post("/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new CreateClassroomRequest("Test Classroom", 30))))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").isNumber(),
                        jsonPath("$.name").value("Test Classroom"),
                        jsonPath("$.capacity").value(30)
                ).andReturn();
        var id = readId(result);

        mockMvc.perform(get("/classrooms/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.name").value("Test Classroom"),
                        jsonPath("$.capacity").value(30)
                );
    }

    @Test
    void shouldUpdateClassroom() throws Exception {
        var id = saveClassroom("Old Name", 10);

        mockMvc.perform(put("/classrooms/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CreateClassroomRequest("New Name", 12))));

        mockMvc.perform(get("/classrooms/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.name").value("New Name"),
                        jsonPath("$.capacity").value(12)
                );
    }

    @Test
    void shouldNotAllowClassroomWithDuplicatedName() throws Exception {
        saveClassroom("Existing Name", 10);

        mockMvc.perform(post("/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new CreateClassroomRequest("Existing Name", 30))))
                .andExpectAll(
                        status().isConflict()
                );
    }

    @ParameterizedTest
    @MethodSource("invalidClassroomRequests")
    void shouldRejectInvalidClassroomRequests(String name, int capacity) throws Exception {
        var request = new CreateClassroomRequest(name, capacity);

        mockMvc.perform(post("/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    private static Stream<Arguments> invalidClassroomRequests() {
        return Stream.of(
                Arguments.of(null, 10),
                Arguments.of("", 10),
                Arguments.of(" ", 10),
                Arguments.of("Name", -1),
                Arguments.of("Name", 0));
    }

    private Integer readId(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    private Integer saveClassroom(String name, int capacity) throws Exception {
        var result = mockMvc.perform(post("/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new CreateClassroomRequest(name, capacity))))
                .andReturn();
        return readId(result);
    }
}
