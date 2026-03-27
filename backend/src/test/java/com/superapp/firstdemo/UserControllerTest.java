package com.superapp.firstdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.superapp.firstdemo.model.User;
import com.superapp.firstdemo.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    // We inject the server side Spring MVC test support.
    // Provides the required methods to test the Spring MVC layer. with perform()
    // method, we can test different HTTP endpoints(GET, POST, PUT, DELETE, etc)
    @Autowired
    private MockMvc mockMvc;

    // We mock the service, here we test only the controller
    @MockBean
    private UserService userServiceMocked;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "user1", password = "pwd", authorities = "ROLE_ADMIN")
    public void getAllUsersTest() throws Exception {
        User userTest1 = new User();
        userTest1.setFirstName("Name1");
        userTest1.setLastName("LastName1");
        User userTest2 = new User();
        userTest2.setFirstName("Name2");
        userTest2.setLastName("LastName2");

        List<User> allUsers = new ArrayList<>(Arrays.asList(userTest1, userTest2));
        Mockito.when(userServiceMocked.getAllUsers()).thenReturn(allUsers);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].firstName", is("Name2")));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", authorities = "ROLE_ADMIN")
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setIdentCard(1105643314);
        user.setFirstName("Arun");
        user.setLastName("Desker");
        user.setEmail("warau@hotmail.com");
        Mockito.when(userServiceMocked.registerUser(ArgumentMatchers.any())).thenReturn(user);
        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.equalTo("Arun")));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", authorities = "ROLE_ADMIN")
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setIdentCard(1105643314);
        user.setFirstName("Arun");
        user.setLastName("Desker");
        user.setEmail("warau@hotmail.com");

        Mockito.when(userServiceMocked.updateUser(ArgumentMatchers.any())).thenReturn(user);
        String json = mapper.writeValueAsString(user);
        mockMvc.perform(put("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.equalTo("Arun")));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", authorities = "ROLE_ADMIN")
    public void testDeleteExample() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setIdentCard(1105643314);
        user.setFirstName("Arun");
        user.setLastName("Desker");
        user.setEmail("warau@hotmail.com");

        Mockito.when(userServiceMocked.deactivateUserById(ArgumentMatchers.any())).thenReturn(user);
        MvcResult requestResult = mockMvc.perform(delete("/api/users/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        String result = requestResult.getResponse().getContentAsString();
        String jsonUser = mapper.writeValueAsString(user);
        assertEquals(result, jsonUser);
    }
}
