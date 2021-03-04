package com.staxter.task2;

import com.staxter.task2.dto.UserLoginRequest;
import com.staxter.task2.dto.UserLoginResponse;
import com.staxter.task2.dto.UserRegistrationRequest;
import com.staxter.task2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@RequiredArgsConstructor
public class APITests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository repository;

    private APIWrapper api;

    @Before
    public void setup() {
        this.api = new APIWrapper(MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build());
    }

    public void clear() {
        repository.clear();
    }

    @Test
    public void testRegistrationFormValidation() throws Exception {
        clear();

        UserRegistrationRequest request = new UserRegistrationRequest();
        this.api.registration(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("METHOD_ARGUMENT_NOT_VALID_EXCEPTION"));

        request.setFirstName("1");
        request.setLastName("1");
        request.setUserName("1");
        request.setPassword("1");

        this.api.registration(request).andExpect(status().isOk());
    }

    @Test
    public void testCheckDuplicates() throws Exception {
        clear();

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setFirstName("1");
        request.setLastName("1");
        request.setUserName("1");
        request.setPassword("1");

        this.api.registration(request)
                .andExpect(status().isOk());

        this.api.registration(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("USER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message").value("A user with the given username already exists"));
    }

    @Test
    public void testLoginIncorrect() throws Exception {
        clear();
        registerDefaultUser();

        UserLoginRequest request = new UserLoginRequest();
        request.setUserName("incorrect");
        request.setPassword("incorrect");

        this.api.login(request)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INCORRECT_USERNAME_OR_PASSWORD"))
                .andExpect(jsonPath("$.message").value("User name or password was incorrect"));

        request.setPassword("1");
        this.api.login(request)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INCORRECT_USERNAME_OR_PASSWORD"))
                .andExpect(jsonPath("$.message").value("User name or password was incorrect"));

        request.setUserName("1");
        this.api.login(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

    }

    @Test
    public void testAuthentication() throws Exception {
        clear();
        registerDefaultUser();

        this.api.hello("incorrectToken")
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_TOKEN"))
                .andExpect(jsonPath("$.message").value("Provided token is invalid or expired"));

        UserLoginRequest request = new UserLoginRequest();
        request.setUserName("1");
        request.setPassword("1");

        UserLoginResponse result = this.api.readContent(
                this.api.login(request),
                UserLoginResponse.class);

        this.api.hello(result.getToken())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello 1!Welcome to staxter!"));
    }

    private void registerDefaultUser() throws Exception{
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setFirstName("1");
        request.setLastName("1");
        request.setUserName("1");
        request.setPassword("1");

        this.api.registration(request);
    }
}
