package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Dish;
import com.github.srg13.votingsystem.model.Menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MenuTestData {

    public static final int MENU1_ID = 100013;
    public static final int OLD_MENU_ID = 100012;

    public static final Menu OLD_MENU = new Menu(OLD_MENU_ID, "Appetizers Sale", LocalDate.of(2020, 11, 11), null);

    public static final List<Menu> MENUS =
            List.of(new Menu(100008, "Special Menu", LocalDate.of(2020, 9, 5), null),
                    new Menu(100010, "Night Pastas", LocalDate.of(2020, 11, 7), null),
                    OLD_MENU);

    public static final String OLD_MENU_JSON =
            "{\"id\":" + OLD_MENU_ID + ",\"name\":\"Appetizers Sale\",\"date\":\"2020-11-11\"," +
                    "\"dishes\":[{\"id\":100036,\"name\":\"Crab Cakes\",\"price\":14.00}," +
                        "{\"id\":100037,\"name\":\"Caprese\",\"price\":11.00}," +
                        "{\"id\":100038,\"name\":\"House Salad\",\"price\":12.00}]}";

    public static final String MENUS_JSON = "[{\"id\":100008,\"name\":\"Special Menu\",\"date\":\"2020-09-05\"}," +
            "{\"id\":100010,\"name\":\"Night Pastas\",\"date\":\"2020-11-07\"}," +
            OLD_MENU_JSON + "]";

    public static Menu getNew() {
        Menu menu = new Menu(null, "New_menu", LocalDate.now(), null);
        menu.setDishes(List.of(new Dish(null, "new1", menu, new BigDecimal("20.00")),
                new Dish(null, "new2", menu, new BigDecimal("30.00"))));
        return menu;
    }
}
