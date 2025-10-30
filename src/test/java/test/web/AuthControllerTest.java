package test.web;

import com.user.UserPortalApplication;
import com.user.dto.RegisterRequest;
import com.user.dto.UserResponse;
import com.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = UserPortalApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void registerHappyPath() throws Exception {
        UserResponse resp = new UserResponse();
        resp.setUsername("jane");
        when(userService.register(any(RegisterRequest.class))).thenReturn(resp);

        mvc.perform(post("/register")
                        .param("username", "jane")
                        .param("email", "jane@example.com")
                        .param("password", "Secret123!")
                        .param("fullName", "Jane Doe")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}