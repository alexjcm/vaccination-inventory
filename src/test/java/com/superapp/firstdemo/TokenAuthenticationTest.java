package com.superapp.firstdemo;

import com.superapp.firstdemo.security.JwtHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static com.superapp.firstdemo.util.AppConstants.AUTHORITIES_CLAIM_NAME;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenAuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vaccines"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", "user3");
        claims.put(AUTHORITIES_CLAIM_NAME, "ROLE_ADMIN");
        String token = jwtHelper.createTokenJwt("user3", claims);

        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vaccines").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", authorities = "ROLE_ADMIN")
    public void givenAdminUser_whenGetVaccines_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vaccines")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2", password = "pwd", authorities = "ROLE_USER")
    public void givenBasicUser_whenGetVaccines_thenForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vaccines")
                        .accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }
}
