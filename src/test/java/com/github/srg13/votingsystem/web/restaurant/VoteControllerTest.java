package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.srg13.votingsystem.util.TestUtil.readListFromResultActions;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.USER2;
import static com.github.srg13.votingsystem.util.VoteTestData.VOTES_OF_USER2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/profile/votes";

    @Autowired
    private VoteDao repository;

    @Test
    void get() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<Vote> votes = readListFromResultActions(result, Vote.class);

        assertThat(votes).usingRecursiveComparison().ignoringFields("restaurant", "user")
                .isEqualTo(VOTES_OF_USER2);
    }
}