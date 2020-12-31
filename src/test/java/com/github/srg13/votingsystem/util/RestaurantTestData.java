package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.dto.RestaurantTo;
import com.github.srg13.votingsystem.model.Restaurant;

import java.util.List;

import static com.github.srg13.votingsystem.util.MenuTestData.MENU1;
import static com.github.srg13.votingsystem.util.MenuTestData.MENU2;
import static com.github.srg13.votingsystem.util.MenuTestData.MENU3;

public class RestaurantTestData {
    public static final int RESTAURANT1_ID = 100003;
    public static final int RESTAURANT2_ID = 100004;
    public static final int RESTAURANT3_ID = 100005;


    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "107 West", "Traditional American food", "2787 Broadway Ave, New York");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "11B EXPRESS", "Pizza, Sushi, Cheeze", "174 Ave B, New York");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID, "Cesario", "Italian food", "601 Sutter St, San Francisco");

    public static final RestaurantTo RESTAURANT1_TO = new RestaurantTo(RESTAURANT1, MENU1);
    public static final RestaurantTo RESTAURANT2_TO = new RestaurantTo(RESTAURANT2, MENU2);
    public static final RestaurantTo RESTAURANT3_TO = new RestaurantTo(RESTAURANT3, MENU3);

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT1, RESTAURANT2, RESTAURANT3);

    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant", "newFood", "newAddress");
    }

    public static RestaurantTo getNewTo() {
        return new RestaurantTo(getNew(), null);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT3_ID, "updatedName", "updatedFood", "updatedAddress");
    }
}
