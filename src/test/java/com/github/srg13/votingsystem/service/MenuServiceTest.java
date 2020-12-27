package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.github.srg13.votingsystem.util.DishTestData.DISHES;
import static com.github.srg13.votingsystem.util.MenuTestData.MENU;
import static com.github.srg13.votingsystem.util.MenuTestData.MENUS_OF_RESTAURANT3;
import static com.github.srg13.votingsystem.util.MenuTestData.MENU_ID;
import static com.github.srg13.votingsystem.util.MenuTestData.getNew;
import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class MenuServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void get() {
        Menu menu = service.get(MENU_ID, RESTAURANT3_ID);

        assertThat(menu).usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(MENU);
        assertThat(menu.getDishes()).usingElementComparatorIgnoringFields("menu").isEqualTo(DISHES);
    }

    @Test
    void getNoExist() {
        assertThrows(NotFoundException.class, () -> service.get(-1, RESTAURANT3_ID));
    }

    @Test
    void delete() {
        service.delete(MENU_ID);
        assertThrows(NotFoundException.class, () -> service.get(MENU_ID, RESTAURANT3_ID));
    }

    @Test
    void getAll() {
        assertThat(service.getAll(RESTAURANT3_ID)).usingElementComparatorIgnoringFields("restaurant", "dishes").isEqualTo(MENUS_OF_RESTAURANT3);
    }

    @Test
    void create() {
        Menu created = service.create(getNew(), RESTAURANT3_ID);
        int newId = created.getId();
        Menu newMenu = getNew();
        newMenu.setId(newId);

        assertThat(created)
                .usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(newMenu);

        assertThat(service.get(newId, RESTAURANT3_ID))
                .usingRecursiveComparison().ignoringFields("restaurant", "dishes").isEqualTo(newMenu);
    }

    @Test
    void createTwiceOnSameDay() {
        service.create(getNew(), RESTAURANT3_ID);
        assertThrows(IllegalRequestDataException.class, () -> service.create(getNew(), RESTAURANT3_ID));
    }
}