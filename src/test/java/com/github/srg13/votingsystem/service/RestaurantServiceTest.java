package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANTS;
import static com.github.srg13.votingsystem.util.RestaurantTestData.getNew;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT3_ID);
        assertThat(restaurant).usingRecursiveComparison().isEqualTo(RESTAURANT);
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
        Restaurant created = service.create(getNew());
        int newId = created.getId();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);

        assertThat(created)
                .usingRecursiveComparison().isEqualTo(newRestaurant);

        assertThat(service.get(newId))
                .usingRecursiveComparison().isEqualTo(newRestaurant);

    }
}