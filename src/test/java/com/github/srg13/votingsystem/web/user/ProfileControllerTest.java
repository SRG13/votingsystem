package com.github.srg13.votingsystem.web.user;

import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.*;
import static com.github.srg13.votingsystem.util.VoteTestData.VOTES_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerTest extends AbstractControllerTest {

    public static final String REST_URL = ProfileController.REST_URL + "/";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(USER_JSON));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isNoContent());
    }

    @Test
    void register() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "register")
                .content(USER_JSON_NEW)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void getVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "vote-history")
                .with(userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(VOTES_JSON));
    }
}