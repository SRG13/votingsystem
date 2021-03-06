package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.model.Menu;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.service.MenuService;
import com.github.srg13.votingsystem.util.exception.NotFoundException;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.github.srg13.votingsystem.util.JsonUtil.writeValue;
import static com.github.srg13.votingsystem.util.MenuTestData.MENU3;
import static com.github.srg13.votingsystem.util.MenuTestData.MENU3_ID;
import static com.github.srg13.votingsystem.util.MenuTestData.MENUS3;
import static com.github.srg13.votingsystem.util.MenuTestData.getNew;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT2_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
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

class MenuControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/restaurants/" + RESTAURANT3_ID + "/menus/";

    @Autowired
    private MenuService service;

    @Autowired
    private VoteDao voteRepository;

    @Test
    void get() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL + MENU3_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Menu menu = readFromResultActions(result, Menu.class);

        assertThat(menu).usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(MENU3);
        assertThat(menu.getDishes()).usingElementComparatorIgnoringFields("menu").isEqualTo(MENU3.getDishes());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU3_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(MENU3_ID, RESTAURANT3_ID));
    }

    @Test
    void deleteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU3_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAll() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<Menu> menus = readListFromResultActions(result, Menu.class);

        assertThat(menus).usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(MENUS3);
    }

    @Test
    void create() throws Exception {
        Menu newMenu = getNew();
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL)
                .content(writeValue(newMenu))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        Menu created = readFromResultActions(result, Menu.class);
        newMenu.setId(created.getId());

        assertThat(created).usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(newMenu);

        assertThat(service.get(created.getId(), RESTAURANT3_ID)).usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(newMenu);
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

    @Test
    void vote() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.post("/restaurants/" + RESTAURANT2_ID + "/menus/100014")
                .with(userHttpBasic(USER2)))
                .andExpect(status().isCreated());

        Vote vote = readFromResultActions(result, Vote.class);

        assertThat(voteRepository.findById(vote.getId()).get()).usingRecursiveComparison()
                .ignoringFields("menu", "user").isEqualTo(vote);
    }

    @Test
    void voteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.post("/restaurants/" + RESTAURANT3_ID + "/menus/100014")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}