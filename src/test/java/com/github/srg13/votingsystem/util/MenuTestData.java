package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Dish;
import com.github.srg13.votingsystem.model.Menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.github.srg13.votingsystem.util.DishTestData.DISHES_OF_MENU1;
import static com.github.srg13.votingsystem.util.DishTestData.DISHES_OF_MENU2;
import static com.github.srg13.votingsystem.util.DishTestData.DISHES_OF_MENU3;

public class MenuTestData {

    public static final int MENU1_ID = 100013;
    public static final int MENU2_ID = 100014;
    public static final int MENU3_ID = 100012;

    public static final Menu MENU1 = new Menu(MENU1_ID, "Burrito Dinner", LocalDate.now(), DISHES_OF_MENU1);
    public static final Menu MENU2 = new Menu(MENU2_ID, "Salad Morning", LocalDate.now(), DISHES_OF_MENU2);
    public static final Menu MENU3 = new Menu(MENU3_ID, "Appetizers Sale", LocalDate.of(2020, 11, 11), DISHES_OF_MENU3);

    public static final List<Menu> MENUS1 =
            List.of(new Menu(100006, "Weekend Brunch", LocalDate.of(2020, 9, 5), null),
                    new Menu(100011, "Seafood Day", LocalDate.of(2020, 11, 11), null),
                    MENU1);
    public static final List<Menu> MENUS2 =
            List.of(new Menu(100007, "Homemade Specialties", LocalDate.of(2020, 9, 5), null),
                    new Menu(100009, "Pizza Break", LocalDate.of(2020, 11, 7), null),
                    MENU2);
    public static final List<Menu> MENUS3 =
            List.of(new Menu(100008, "Special Menu", LocalDate.of(2020, 9, 5), null),
                    new Menu(100010, "Night Pastas", LocalDate.of(2020, 11, 7), null),
                    MENU3);

    public static Menu getNew() {
        Menu menu = new Menu(null, "New_menu", LocalDate.now(), null);
        menu.setDishes(List.of(new Dish(null, "new1", menu, new BigDecimal("20.00")),
                new Dish(null, "new2", menu, new BigDecimal("30.00"))));
        return menu;
    }
}
