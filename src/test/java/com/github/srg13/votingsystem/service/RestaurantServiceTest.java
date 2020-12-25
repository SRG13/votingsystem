package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dto.RestaurantTo;
import com.github.srg13.votingsystem.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU;
import static com.github.srg13.votingsystem.util.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void get() {
        RestaurantTo restaurantTo = service.get(RESTAURANT3_ID);

        assertThat(restaurantTo).usingRecursiveComparison().ignoringFields("menuOfDay").isEqualTo(RESTAURANT_TO);
        assertThat(restaurantTo.getMenuOfDay()).usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(OLD_MENU);
    }

    @Test
    void getNotExist() {
        assertThrows(NotFoundException.class, () -> service.get(-1));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT3_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT3_ID));
    }

    @Test
    void getAll() {
        assertThat(service.getAll()).usingRecursiveComparison().isEqualTo(RESTAURANTS);
    }

    @Test
    void create() {
        RestaurantTo created = service.create(getNew());
        int newId = created.getId();
        RestaurantTo newRestaurantTo = getNewTo();
        newRestaurantTo.setId(newId);

        assertThat(created)
                .usingRecursiveComparison().isEqualTo(newRestaurantTo);

        assertThat(service.get(newId))
                .usingRecursiveComparison().isEqualTo(newRestaurantTo);

    }
}