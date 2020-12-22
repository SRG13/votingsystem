package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Dish;
import com.github.srg13.votingsystem.service.DishService;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.DishTestData.DISH1_ID;
import static com.github.srg13.votingsystem.util.DishTestData.DISHES_JSON;
import static com.github.srg13.votingsystem.util.DishTestData.DISH_JSON;
import static com.github.srg13.votingsystem.util.DishTestData.getNew;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.ADMIN;
import static com.github.srg13.votingsystem.util.UserTestData.USER1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/restaurants/" + RESTAURANT3_ID + "/menus/" + OLD_MENU_ID + "/dishes/";

    @Autowired
    private DishService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(DISH_JSON));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID));
    }

    @Test
    void deleteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(DISHES_JSON));
    }

    @Test
    void create() throws Exception {
        Dish newDish = getNew();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL)
                .content(writeValue(newDish))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        Dish result = readValue(json, Dish.class);
        newDish.setId(result.getId());

        assertThat(result).usingRecursiveComparison().ignoringFields("menu").isEqualTo(newDish);

        assertThat(service.get(result.getId())).usingRecursiveComparison().ignoringFields("menu").isEqualTo(newDish);
    }

    @Test
    void createNotAllowed() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER1))
                .content(writeValue(newDish))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}