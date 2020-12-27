package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Dish;
import com.github.srg13.votingsystem.model.Menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MenuTestData {

    public static final int MENU_ID = 100012;

    public static final Menu MENU = new Menu(MENU_ID, "Appetizers Sale", LocalDate.of(2020, 11, 11), null);

    public static final List<Menu> MENUS_OF_RESTAURANT3 =
            List.of(new Menu(100008, "Special Menu", LocalDate.of(2020, 9, 5), null),
                    new Menu(100010, "Night Pastas", LocalDate.of(2020, 11, 7), null),
                    MENU);

    public static Menu getNew() {
        Menu menu = new Menu(null, "New_menu", LocalDate.now(), null);
        menu.setDishes(List.of(new Dish(null, "new1", menu, new BigDecimal("20.00")),
                new Dish(null, "new2", menu, new BigDecimal("30.00"))));
        return menu;
    }
}
