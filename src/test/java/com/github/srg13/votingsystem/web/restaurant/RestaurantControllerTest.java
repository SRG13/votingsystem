package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.dto.RestaurantTo;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Restaurant;
import com.github.srg13.votingsystem.service.RestaurantService;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.RestaurantTestData.*;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.ADMIN;
import static com.github.srg13.votingsystem.util.UserTestData.USER1;
import static com.github.srg13.votingsystem.util.UserTestData.USER2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    public static final String REST_URL = RestaurantController.REST_URL + "/";

    @Autowired
    private RestaurantService service;

    @Autowired
    private RestaurantDao repository;

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

        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT3_ID));
    }

    @Test
    void deleteNotAllowed() throws Exception {
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
                .content(writeValue(newRestaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        RestaurantTo result = readValue(json, RestaurantTo.class);
        RestaurantTo newRestaurantTo = getNewTo();
        newRestaurantTo.setId(result.getId());

        assertThat(result).usingRecursiveComparison().isEqualTo(newRestaurantTo);

        assertThat(service.get(result.getId())).usingRecursiveComparison().isEqualTo(newRestaurantTo);
    }

    @Test
    void createNotAllowed() throws Exception {
        Restaurant newRestaurant = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .content(writeValue(newRestaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update() throws Exception {
        Restaurant updatedRestaurant = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT3_ID)
                .content(writeValue(updatedRestaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(RESTAURANT3_ID).get()).usingRecursiveComparison().isEqualTo(updatedRestaurant);
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT3_ID)
                .with(userHttpBasic(USER2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void voteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT3_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}