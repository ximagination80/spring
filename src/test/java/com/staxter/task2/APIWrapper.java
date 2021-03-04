package com.staxter.task2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxter.task2.dto.UserLoginRequest;
import com.staxter.task2.dto.UserRegistrationRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class APIWrapper {

    private static final String USERSERVICE_REGISTER = "/userservice/register";
    private static final String USERSERVICE_LOGIN = "/userservice/login";
    private static final String USERSERVICE_HELLO = "/userservice/hello";

    private final MockMvc mockMvc;

    public APIWrapper(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions registration(UserRegistrationRequest request) throws Exception {
        return this.mockMvc.perform(postJSON(USERSERVICE_REGISTER, request)).andDo(print());
    }

    public ResultActions login(UserLoginRequest request) throws Exception {
        return this.mockMvc.perform(postJSON(USERSERVICE_LOGIN, request)).andDo(print());
    }

    public <T> T readContent(ResultActions actions, Class<T> t) throws Exception {
        String content = actions.andReturn().getResponse().getContentAsString();
        return new ObjectMapper().readValue(content, t);
    }

    public ResultActions hello(String token) throws Exception {
        return this.mockMvc.perform(get(USERSERVICE_HELLO)
                .header("Authorization", "Bearer " + token))
                .andDo(print());
    }

    private static MockHttpServletRequestBuilder postJSON(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
