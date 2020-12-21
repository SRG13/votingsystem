package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Restaurant;

public class RestaurantTestData {
    public static final int RESTAURANT1_ID = 100003;
    public static final int RESTAURANT2_ID = 100004;
    public static final int RESTAURANT3_ID = 100005;

    public static final String RESTAURANT_JSON = "{\"id\":" + RESTAURANT3_ID + ",\"name\":\"Cesario\"}";

    public static final String RESTAURANTS_JSON =
            "[{\"id\":" + RESTAURANT1_ID + ",\"name\":\"107 West\"},{\"id\":" + RESTAURANT2_ID + ",\"name\":\"11B EXPRESS\"}," +
                    RESTAURANT_JSON + "]";


    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant");
    }
}
