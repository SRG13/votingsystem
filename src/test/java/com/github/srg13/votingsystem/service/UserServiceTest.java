package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Role;
import com.github.srg13.votingsystem.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;

import static com.github.srg13.votingsystem.util.UserTestData.USER1;
import static com.github.srg13.votingsystem.util.UserTestData.USER1_ID;
import static com.github.srg13.votingsystem.util.UserTestData.USERS;
import static com.github.srg13.votingsystem.util.UserTestData.getNew;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    @Transactional
    void get() {
        User user = service.get(USER1_ID);
        assertThat(user).usingRecursiveComparison().ignoringFields("password", "registered").isEqualTo(USER1);
    }

    @Test
    void getNotExist() {
        assertThrows(NotFoundException.class, () -> service.get(-1));
    }

    @Test
    void delete() {
        service.delete(USER1_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER1_ID));
    }

    @Test
    void getAll() {
        assertThat(service.getAll()).usingElementComparatorIgnoringFields("registered", "password").isEqualTo(USERS);
    }

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);

        assertThat(created)
                .usingRecursiveComparison().ignoringFields("password", "registered").isEqualTo(newUser);

        assertThat(service.get(newId))
                .usingRecursiveComparison().ignoringFields("password", "registered").isEqualTo(newUser);

    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@gmail.com", "newPass", LocalDateTime.now(), true, EnumSet.of(Role.USER))));
    }
}