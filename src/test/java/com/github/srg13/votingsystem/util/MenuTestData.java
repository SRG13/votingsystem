package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Menu;

import java.time.LocalDate;
import java.util.List;

public class MenuTestData {

    public static final int MENU1_ID = 100013;
    public static final int MENU2_ID = 100014;
    public static final int OLD_MENU = 100012;

    public static final Menu MENU_1 = new Menu(MENU1_ID, "Burrito Dinner", LocalDate.of(2020, 12, 16));

    //restaurant=100003 menu's
    public static final List<Menu> MENUS =
            List.of(new Menu(100006, "Weekend Brunch", LocalDate.of(2020, 9, 5)),
                    new Menu(100011, "Seafood Day", LocalDate.of(2020, 11, 11)),
                    MENU_1);


    public static Menu getNew() {
        return new Menu(null, "New_menu", LocalDate.now());
    }
}
