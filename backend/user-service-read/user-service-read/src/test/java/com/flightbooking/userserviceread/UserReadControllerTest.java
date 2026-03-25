package com.flightbooking.userserviceread;

import com.flightbooking.userserviceread.controller.UserReadController;
import com.flightbooking.userserviceread.dto.UserResponseDTO;
import com.flightbooking.userserviceread.service.UserReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserReadController.class)
public class UserReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserReadService userReadService;

    @Test
    public void testGetAllUsers_returnsOk() throws Exception {
        when(userReadService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById_returnsOk() throws Exception {
        when(userReadService.getUserById(1L)).thenReturn(new UserResponseDTO());

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserByEmail_returnsOk() throws Exception {
        when(userReadService.getUserByEmail("test@test.com")).thenReturn(new UserResponseDTO());

        mockMvc.perform(get("/api/users/email/test@test.com"))
                .andExpect(status().isOk());
    }
}