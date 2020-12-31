package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Dish;

import java.math.BigDecimal;
import java.util.List;

public class DishTestData {

    public static final List<Dish> DISHES_OF_MENU1 =
            List.of(new Dish(100039, "Cheese Burrito", new BigDecimal("17.00")),
                    new Dish(100040, "Smoked Chicken", new BigDecimal("16.00")),
                    new Dish(100041, "Pork Burrito", new BigDecimal("11.50")),
                    new Dish(100042, "Steak & Egg Burrito", new BigDecimal("13.00")),
                    new Dish(100043, "Tongue Burrito", new BigDecimal("15.50")));

    public static final List<Dish> DISHES_OF_MENU2 =
            List.of(new Dish(100044, "Arugula Salad", new BigDecimal("9.50")),
                    new Dish(100045, "Spinach with Goat Cheese Salad", new BigDecimal("12.00")),
                    new Dish(100046, "Caesar Salad", new BigDecimal("10.00")),
                    new Dish(100047, "Mescaline Mix with Grilled Chicken", new BigDecimal("12.00")));

    public static final List<Dish> DISHES_OF_MENU3 =
            List.of(new Dish(100036, "Crab Cakes", new BigDecimal("14.00")),
                    new Dish(100037, "Caprese", new BigDecimal("11.00")),
                    new Dish(100038, "House Salad", new BigDecimal("12.00")));
}
