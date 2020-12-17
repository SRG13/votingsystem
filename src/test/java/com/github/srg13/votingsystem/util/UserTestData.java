package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Role;
import com.github.srg13.votingsystem.model.User;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

public class UserTestData {
    public static final int ADMIN_ID = 100000;
    public static final int USER1_ID = 100001;
    public static final int USER_ID_NOT_VOTED = 100002;

    public static final User USER =
            new User(USER1_ID, "Sergey", "user@gmail.com", "pass123", LocalDateTime.now(), true, EnumSet.of(Role.USER), null);

    public static final List<User> USERS = List.of(new User(ADMIN_ID, "Petr", "admin@yandex.ru", true, EnumSet.of(Role.USER, Role.ADMIN)),
            USER, new User(USER_ID_NOT_VOTED, "Anna", "bar@gmail.com", true, EnumSet.of(Role.USER)));

    public static User getNew() {
        return new User(null, "New_menu", "hoho@gmail.com", "qwerty", LocalDateTime.of(2020, 10, 3, 10, 3), true, EnumSet.of(Role.USER), null);
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setEmail("mrbugsy@mail.ru");
        return updated;
    }

}
