package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Dish;

import java.math.BigDecimal;

public class DishTestData {

    public static final int DISH1_ID = 100036;

    public static final String DISH_JSON = "{\"id\":" + DISH1_ID + ",\"name\":\"Crab Cakes\",\"price\":14.00}";

    public static final String DISHES_JSON = "[" + DISH_JSON +
            ",{\"id\":100037,\"name\":\"Caprese\",\"price\":11.00}," +
            "{\"id\":100038,\"name\":\"House Salad\",\"price\":12.00}]";

    public static Dish getNew() {
        return new Dish(null, "newDish", null, new BigDecimal(20));
    }
}
