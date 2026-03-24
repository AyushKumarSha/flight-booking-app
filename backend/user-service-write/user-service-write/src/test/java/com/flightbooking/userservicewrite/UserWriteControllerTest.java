package com.flightbooking.userservicewrite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.flightbooking.userservicewrite.controller.UserWriteController;
import com.flightbooking.userservicewrite.dto.AuthResponseDTO;
import com.flightbooking.userservicewrite.service.UserWriteService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserWriteController.class)
@WithMockUser
public class UserWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserWriteService userWriteService;

    @Test
    public void testRegister_returnsOk() throws Exception {
        AuthResponseDTO response = new AuthResponseDTO("User registered successfully", "test@test.com", "USER", 1L);

        when(userWriteService.register(any())).thenReturn(response);

        mockMvc.perform(post("/api/users/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\":\"Test User\",\"email\":\"test@test.com\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_returnsOk() throws Exception {
        AuthResponseDTO response = new AuthResponseDTO("Login successful", "test@test.com", "USER", 1L);

        when(userWriteService.login(any())).thenReturn(response);

        mockMvc.perform(post("/api/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk());
    }
}