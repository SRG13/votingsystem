package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.DishDao;
import com.github.srg13.votingsystem.dao.MenuDao;
import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.model.Menu;
import com.github.srg13.votingsystem.util.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.github.srg13.votingsystem.util.ValidationUtil.checkNew;

@Service
public class MenuService {

    private final MenuDao menuRepository;

    private final RestaurantDao restaurantRepository;

    private final DishDao dishRepository;

    @Autowired
    public MenuService(MenuDao menuRepository, RestaurantDao restaurantRepository, DishDao dishRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public Menu get(int id, int restaurantId) {
        return menuRepository.findByIdAndRestaurantIdWithDishes(id, restaurantId)
                .orElseThrow(() -> new NotFoundException("Menu with id=" + id + " for restaurant=" + restaurantId + " not found."));
    }

    @Transactional
    public void delete(int id, int restaurantId) {
        checkMenuForRestaurantExist(id, restaurantId);
        menuRepository.deleteById(id);
    }

    public List<Menu> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        checkNew(menu);
        checkRestaurantExist(restaurantId);
        checkExistForThisDay(menu.getDate(), restaurantId);

        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        Menu created = menuRepository.save(menu);
        dishRepository.saveAll(menu.getDishes());

        return created;
    }

    private void checkMenuForRestaurantExist(int id, int restaurantId) {
        if (!menuRepository.existsByIdAndRestaurantId(id, restaurantId)) {
            throw new IllegalRequestDataException("Menu=" + id + " for Restaurant=" + restaurantId + "not exist.");
        }
    }

    private void checkRestaurantExist(int restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new NotFoundException("Restaurant with id=" + restaurantId + " not found.");
        }
    }

    private void checkExistForThisDay(LocalDate date, int restaurantId) {
        if (menuRepository.existsByRestaurantIdAndDate(restaurantId, date)) {
            throw new IllegalRequestDataException("Menu at " + date + " date already exist.");
        }
    }
}
