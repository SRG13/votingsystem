package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.github.srg13.votingsystem.util.DishTestData.DISH;
import static com.github.srg13.votingsystem.util.DishTestData.DISH1_ID;
import static com.github.srg13.votingsystem.util.DishTestData.DISHES;
import static com.github.srg13.votingsystem.util.DishTestData.getNew;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    void get() {
        Dish dish = service.get(DISH1_ID);
        assertThat(dish).usingRecursiveComparison().ignoringFields("menu").isEqualTo(DISH);
    }

    @Test
    void getNotExist() {
        assertThrows(NotFoundException.class, () -> service.get(-1));
    }

    @Test
    void delete() {
        service.delete(DISH1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID));
    }

    @Test
    void getAll() {
        assertThat(service.getAll(OLD_MENU_ID)).usingElementComparatorIgnoringFields("menu").isEqualTo(DISHES);
    }

    @Test
    void create() {
        Dish created = service.create(getNew(), OLD_MENU_ID);
        int newId = created.getId();
        Dish newDish = getNew();
        newDish.setId(newId);

        assertThat(created)
                .usingRecursiveComparison().ignoringFields("menu").isEqualTo(newDish);

        assertThat(service.get(newId))
                .usingRecursiveComparison().ignoringFields("menu").isEqualTo(newDish);
    }
}