package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Restaurant;

import java.util.List;

public class RestaurantTestData {
    public static final int RESTAURANT1_ID = 100003;
    public static final int RESTAURANT2_ID = 100004;
    public static final int RESTAURANT3_ID = 100005;

    public static final Restaurant RESTAURANT = new Restaurant(RESTAURANT3_ID, "Cesario");

    public static final List<Restaurant> RESTAURANTS = List.of(new Restaurant(RESTAURANT1_ID, "107 West"),
            new Restaurant(RESTAURANT2_ID, "11B EXPRESS"), RESTAURANT);

    public static final String RESTAURANT_JSON = "{\"id\":" + RESTAURANT3_ID + ",\"name\":\"Cesario\"}";

    public static final String RESTAURANTS_JSON =
            "[{\"id\":" + RESTAURANT1_ID + ",\"name\":\"107 West\"},{\"id\":" + RESTAURANT2_ID + ",\"name\":\"11B EXPRESS\"}," +
                    RESTAURANT_JSON + "]";


    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant");
    }
}
