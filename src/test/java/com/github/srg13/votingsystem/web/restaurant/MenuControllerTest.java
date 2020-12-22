package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Menu;
import com.github.srg13.votingsystem.service.MenuService;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.MenuTestData.MENUS_JSON;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_ID;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_JSON;
import static com.github.srg13.votingsystem.util.MenuTestData.getNew;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.ADMIN;
import static com.github.srg13.votingsystem.util.UserTestData.USER1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/restaurants/" + RESTAURANT3_ID + "/menus/";

    @Autowired
    private MenuService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + OLD_MENU_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(OLD_MENU_JSON));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + OLD_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(OLD_MENU_ID));
    }

    @Test
    void deleteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + OLD_MENU_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(MENUS_JSON));
    }

    @Test
    void create() throws Exception {
        Menu newMenu = getNew();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL)
                .content(writeValue(newMenu))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        Menu created = readValue(json, Menu.class);
        newMenu.setId(created.getId());

        assertThat(created).usingRecursiveComparison().isEqualTo(newMenu);

        assertThat(service.get(created.getId())).usingRecursiveComparison().ignoringFields("restaurant").isEqualTo(newMenu);

    }

    @Test
    void createNotAllowed() throws Exception {
        Menu newMenu = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .content(writeValue(newMenu))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }
}