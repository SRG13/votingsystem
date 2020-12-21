package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.model.Restaurant;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.RestaurantTestData.*;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.ADMIN;
import static com.github.srg13.votingsystem.util.UserTestData.USER1;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    public static final String REST_URL = RestaurantController.REST_URL + "/";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(RESTAURANT_JSON));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT3_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT3_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(RESTAURANTS_JSON));
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL)
                .content(asJsonString(newRestaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void createNotAdmin() throws Exception {
        Restaurant newRestaurant = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .content(asJsonString(newRestaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());

    }
}