package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Dish;

import java.math.BigDecimal;
import java.util.List;

public class DishTestData {

    public static final int DISH1_ID = 100036;

    public static final Dish DISH = new Dish(DISH1_ID, "Crab Cakes", new BigDecimal("14.00"));

    public static final List<Dish> DISHES = List.of(DISH,
            new Dish(100037, "Caprese", new BigDecimal("11.00")),
            new Dish(100038, "House Salad", new BigDecimal("12.00")));

    public static final String DISH_JSON = "{\"id\":" + DISH1_ID + ",\"name\":\"Crab Cakes\",\"price\":14.00}";

    public static final String DISHES_JSON = "[" + DISH_JSON +
            ",{\"id\":100037,\"name\":\"Caprese\",\"price\":11.00}," +
            "{\"id\":100038,\"name\":\"House Salad\",\"price\":12.00}]";

    public static Dish getNew() {
        return new Dish(null, "newDish", new BigDecimal("20.00"));
    }
}
