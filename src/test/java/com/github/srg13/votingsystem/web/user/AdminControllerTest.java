package com.github.srg13.votingsystem.web.user;

import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest extends AbstractControllerTest {

    public static final String REST_URL = AdminController.REST_URL + "/";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_ID_NOT_VOTED)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(USER_JSON));
    }

    @Test
    void getNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_ID_NOT_VOTED)
                .with(userHttpBasic(USER2)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by?email=" + USER2.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(USER_JSON));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(USERS_JSON));
    }

    @Test
    void createWithLocation() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .content(USER_JSON_NEW)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}