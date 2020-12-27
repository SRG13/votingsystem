package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Dish;

import java.math.BigDecimal;
import java.util.List;

public class DishTestData {

    public static final List<Dish> DISHES =
            List.of(new Dish(100036, "Crab Cakes", null, new BigDecimal("14.00")),
                    new Dish(100037, "Caprese", null, new BigDecimal("11.00")),
                    new Dish(100038, "House Salad", null, new BigDecimal("12.00")));
}
