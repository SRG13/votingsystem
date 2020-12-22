package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.srg13.votingsystem.util.MenuTestData.MENU1_ID;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT1_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.USER1;
import static com.github.srg13.votingsystem.util.UserTestData.USER2;
import static com.github.srg13.votingsystem.util.VoteTestData.VOTE_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    public static final String REST_URL_1 = "/restaurants/" + RESTAURANT3_ID + "/menus/" + OLD_MENU_ID + "/vote/";
    public static final String REST_URL_2 = "/restaurants/" + RESTAURANT1_ID + "/menus/" + MENU1_ID + "/vote/";

    @Autowired
    private VoteDao repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(VOTE_JSON));
    }

    @Test
    void create() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL_2)
                .with(userHttpBasic(USER2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        Vote result = readValue(json, Vote.class);

        assertThat(repository.findById(result.getId()).get()).usingRecursiveComparison().ignoringFields("menu", "user").isEqualTo(result);
    }
}