package com.github.srg13.votingsystem.web.user;

import com.github.srg13.votingsystem.model.User;
import com.github.srg13.votingsystem.service.UserService;
import com.github.srg13.votingsystem.util.exception.NotFoundException;
import com.github.srg13.votingsystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.srg13.votingsystem.util.TestUtil.readFromResultActions;
import static com.github.srg13.votingsystem.util.TestUtil.userHttpBasic;
import static com.github.srg13.votingsystem.util.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerTest extends AbstractControllerTest {

    public static final String REST_URL = ProfileController.REST_URL + "/";

    @Autowired
    private UserService service;

    @Test
    void get() throws Exception {
        ResultActions result = perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        User user = readFromResultActions(result, User.class);

        assertThat(user).usingRecursiveComparison().ignoringFields("password").isEqualTo(USER2);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(USER2_ID));
    }

    @Test
    void register() throws Exception {
        User newUser = getNew();
        ResultActions result = perform(MockMvcRequestBuilders.post(REST_URL + "register")
                .content(USER_JSON_NEW)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        User user = readFromResultActions(result, User.class);
        newUser.setId(user.getId());

        assertThat(user).usingRecursiveComparison().ignoringFields("password").isEqualTo(newUser);

        assertThat(service.get(user.getId())).usingRecursiveComparison().ignoringFields("password").isEqualTo(newUser);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(USER1))
                .content(USER_JSON_UPDATED)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(service.get(updated.getId())).usingRecursiveComparison().ignoringFields("password").isEqualTo(updated);
    }
}