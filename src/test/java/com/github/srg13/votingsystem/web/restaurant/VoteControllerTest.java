package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.MenuTestData.MENU1_ID;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT1_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.USER2;
import static com.github.srg13.votingsystem.util.VoteTestData.VOTE_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    public static final String REST_URL_1 = "/restaurants/" + RESTAURANT3_ID + "/menus/" + OLD_MENU_ID + "/vote/";
    public static final String REST_URL_2 = "/restaurants/" + RESTAURANT1_ID + "/menus/" + MENU1_ID + "/vote/";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_1)
                .with(httpBasic("user@gmail.com", "pass123")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(VOTE_JSON));
    }

    @Test
    void create() throws Exception {
        Vote newVote = new Vote();

        perform(MockMvcRequestBuilders.post(REST_URL_2)
                .content(asJsonString(newVote))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}