package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.model.Dish;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.DishTestData.*;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.ADMIN;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/restaurants/" + RESTAURANT3_ID + "/menus/" + OLD_MENU_ID + "/dishes/";

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
                .with((userHttpBasic(ADMIN))))
                .andExpect(status().isNoContent());
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
        perform(MockMvcRequestBuilders.post(REST_URL)
                .content(asJsonString(newDish))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.authentication(
                        (new UsernamePasswordAuthenticationToken("admin@yandex.ru", "admin")))))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}