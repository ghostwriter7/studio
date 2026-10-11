package com.rozplochowski.studio.classrooms.adapter.in.web;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void shouldCreateAndPersistClassroom() throws Exception {
        var result = mockMvc.perform(post("/classrooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Test Classroom",
                            "capacity": 30
                        }
                        """))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").isNumber(),
                        jsonPath("$.name").value("Test Classroom"),
                        jsonPath("$.capacity").value(30)
                ).andReturn();

        var id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/classrooms/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.name").value("Test Classroom"),
                        jsonPath("$.capacity").value(30)
                );
    }
}
