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

    public static final User ADMIN =
            new User(ADMIN_ID, "Petr", "admin@yandex.ru", "admin",
                    LocalDateTime.of(2020, 1, 30, 12, 0, 44), true,
                    EnumSet.of(Role.USER, Role.ADMIN));

    public static final User USER1 =
            new User(USER1_ID, "Sergey", "user@gmail.com", "pass123",
                    LocalDateTime.of(2020, 2, 5, 13, 5, 11), true,
                    EnumSet.of(Role.USER));

    public static final User USER2 =
            new User(USER_ID_NOT_VOTED, "Anna", "bar@gmail.com", "s3cr3t",
                    LocalDateTime.of(2020, 2, 19, 9, 13, 42), true,
                    EnumSet.of(Role.USER));


    public static final List<User> USERS = List.of(ADMIN, USER1, USER2);

    public static final String USER_JSON = "{\"id\":100002,\"name\":\"Anna\",\"email\":\"bar@gmail.com\",\"registered\":\"2020-02-19T09:13:42\",\"enabled\":true,\"roles\":[\"USER\"]}";

    public static final String USER_JSON_NEW = "{\"name\":\"Fedr\",\"email\":\"fedya@gmail.com\",\"password\":\"qwerty123\",\"registered\":\"2020-10-03T10:03:02\"}";

    public static final String USER_JSON_UPDATED = "{\"id\":100001,\"name\":\"UpdatedName\",\"email\":\"mrbugsy@mail.ru\",\"password\":\"pass123\",\"registered\":\"2020-02-05T13:05:11\"}";

    public static final String USERS_JSON = "[{\"id\":100000,\"name\":\"Petr\",\"email\":\"admin@yandex.ru\",\"registered\":\"2020-01-30T12:00:44\",\"enabled\":true,\"roles\":[\"ADMIN\",\"USER\"]}," +
            "{\"id\":100001,\"name\":\"Sergey\",\"email\":\"user@gmail.com\",\"registered\":\"2020-02-05T13:05:11\",\"enabled\":true,\"roles\":[\"USER\"]}," +
            "{\"id\":100002,\"name\":\"Anna\",\"email\":\"bar@gmail.com\",\"registered\":\"2020-02-19T09:13:42\",\"enabled\":true,\"roles\":[\"USER\"]}]";

    public static User getNew() {
        return new User(null, "Fedr", "fedya@gmail.com", "qwerty123", LocalDateTime.of(2020, 10, 3, 10, 3, 2), true, EnumSet.of(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setEmail("mrbugsy@mail.ru");
        return updated;
    }

}
