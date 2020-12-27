package com.github.srg13.votingsystem.web.user;

import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.User;
import com.github.srg13.votingsystem.service.UserService;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.github.srg13.votingsystem.util.TestUtil.readFromResultActions;
import static com.github.srg13.votingsystem.util.TestUtil.readListFromResultActions;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest extends AbstractControllerTest {

    public static final String REST_URL = AdminController.REST_URL + "/";

    @Autowired
    private UserService service;

    @Test
    void get() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL + USER2_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        User user = readFromResultActions(result, User.class);

        assertThat(user).usingRecursiveComparison().ignoringFields("password").isEqualTo(USER2);

    }

    @Test
    void getNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER2_ID)
                .with(userHttpBasic(USER2)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getByEmail() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL + "by?email=" + USER2.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        User user = readFromResultActions(result, User.class);

        assertThat(user).usingRecursiveComparison().ignoringFields("password").isEqualTo(USER2);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(USER1_ID));
    }

    @Test
    void getAll() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<User> users = readListFromResultActions(result, User.class);

        assertThat(users).usingElementComparatorIgnoringFields("password").isEqualTo(USERS);
    }

    @Test
    void createWithLocation() throws Exception {
        User newUser = getNew();
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .content(USER_JSON_NEW)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        User user = readFromResultActions(result, User.class);
        newUser.setId(user.getId());

        assertThat(result).usingRecursiveComparison().ignoringFields("password").isEqualTo(newUser);

        assertThat(service.get(user.getId())).usingRecursiveComparison().ignoringFields("password").isEqualTo(newUser);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + USER1_ID)
                .with(userHttpBasic(ADMIN))
                .content(USER_JSON_UPDATED)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(service.get(updated.getId())).usingRecursiveComparison().ignoringFields("password").isEqualTo(updated);

    }
}