package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.dto.RestaurantTo;
import com.github.srg13.votingsystem.util.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Restaurant;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.service.RestaurantService;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.github.srg13.votingsystem.util.JsonUtil.writeValue;
import static com.github.srg13.votingsystem.util.RestaurantTestData.*;
import static com.github.srg13.votingsystem.util.TestUtil.readFromResultActions;
import static com.github.srg13.votingsystem.util.TestUtil.readListFromResultActions;
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
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantDao restaurantRepository;

    @Autowired
    private VoteDao voteRepository;

    @Test
    void get() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        RestaurantTo restaurantTo = readFromResultActions(result, RestaurantTo.class);

        assertThat(restaurantTo).usingRecursiveComparison().ignoringFields("menuOfDay").isEqualTo(RESTAURANT3_TO);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT3_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT3_ID));
    }

    @Test
    void deleteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT3_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<Restaurant> restaurants = readListFromResultActions(result, Restaurant.class);

        assertThat(restaurants).usingRecursiveComparison().isEqualTo(RESTAURANTS);
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL)
                .content(writeValue(newRestaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        RestaurantTo restaurantTo = readFromResultActions(result, RestaurantTo.class);
        RestaurantTo newRestaurantTo = getNewTo();
        newRestaurantTo.setId(restaurantTo.getId());

        assertThat(result).usingRecursiveComparison().isEqualTo(newRestaurantTo);

        assertThat(restaurantService.get(restaurantTo.getId())).usingRecursiveComparison().isEqualTo(newRestaurantTo);
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
                .content(writeValue(getUpdated()))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertThat(restaurantRepository.findById(RESTAURANT3_ID).get()).usingRecursiveComparison().isEqualTo(updatedRestaurant);
    }

    @Test
    void updateNotAllowed() throws Exception {
        Restaurant updatedRestaurant = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT3_ID)
                .content(writeValue(updatedRestaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void vote() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT3_ID)
                .with(userHttpBasic(USER2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Vote vote = readFromResultActions(result, Vote.class);

        assertThat(voteRepository.findById(vote.getId()).get()).usingRecursiveComparison()
                .ignoringFields("restaurant", "user").isEqualTo(vote);
    }

    @Test
    void voteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT3_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}