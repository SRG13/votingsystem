package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.DishDao;
import com.github.srg13.votingsystem.dao.MenuDao;
import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.github.srg13.votingsystem.util.ValidationUtil.checkNew;

@Service
public class MenuService {

    private final MenuDao repository;

    private final RestaurantDao restaurantRepository;

    private final DishDao dishRepository;

    @Autowired
    public MenuService(MenuDao repository, RestaurantDao restaurantRepository, DishDao dishRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public Menu get(int id) {
        return repository.findByIdWithDishes(id)
                .orElseThrow(() -> new NotFoundException("Menu with id=" + id + " not found."));
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Menu> getAll(int restaurantId) {
        return repository.findAllByRestaurantId(restaurantId);
    }

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        checkNew(menu);
        checkExistForThisDay(menu.getDate(), restaurantId);

        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        Menu created = repository.save(menu);
        dishRepository.saveAll(menu.getDishes());

        return created;
    }

    private void checkExistForThisDay(LocalDate date, int restaurantId) {
        if (repository.findByRestaurantIdAndDate(restaurantId, date).isPresent()) {
            throw new IllegalRequestDataException("Menu at " + date + " date already exist.");
        }
    }
}
