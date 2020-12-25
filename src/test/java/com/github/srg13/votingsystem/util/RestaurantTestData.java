package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.dto.RestaurantTo;
import com.github.srg13.votingsystem.model.Restaurant;

import java.util.List;

public class RestaurantTestData {
    public static final int RESTAURANT1_ID = 100003;
    public static final int RESTAURANT2_ID = 100004;
    public static final int RESTAURANT3_ID = 100005;

    public static final Restaurant RESTAURANT = new Restaurant(RESTAURANT3_ID, "Cesario", "Italian food", "601 Sutter St, San Francisco");
    public static final RestaurantTo RESTAURANT_TO = new RestaurantTo(RESTAURANT);

    public static final List<Restaurant> RESTAURANTS = List.of(new Restaurant(RESTAURANT1_ID, "107 West", "Traditional American food", "2787 Broadway Ave, New York"),
            new Restaurant(RESTAURANT2_ID, "11B EXPRESS", "Pizza, Sushi, Cheeze", "174 Ave B, New York"),
            new Restaurant(RESTAURANT3_ID, "Cesario", "Italian food", "601 Sutter St, San Francisco"));

    public static final String RESTAURANT_JSON = "{\"id\":" + RESTAURANT3_ID + ",\"name\":\"Cesario\",\"description\":\"Italian food\"}";

    public static final String RESTAURANTS_JSON =
            "[{\"id\":" + RESTAURANT1_ID + ",\"name\":\"107 West\",\"description\":\"Traditional American food\"},{\"id\":" + RESTAURANT2_ID + ",\"name\":\"11B EXPRESS\",\"description\":\"Pizza, Sushi, Cheeze\"}," +
                    RESTAURANT_JSON + "]";


    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant", "newFood", "newAddress");
    }

    public static RestaurantTo getNewTo() {
        return new RestaurantTo(getNew());
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT3_ID, "updatedName", "updatedFood", "updatedAddress");
    }
}
