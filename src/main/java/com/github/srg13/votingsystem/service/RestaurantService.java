package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.MenuDao;
import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.dto.RestaurantTo;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.srg13.votingsystem.util.ValidationUtil.assureIdConsistent;
import static com.github.srg13.votingsystem.util.ValidationUtil.checkNew;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantRepository;

    private final MenuDao menuRepository;

    @Autowired
    public RestaurantService(RestaurantDao restaurantRepository, MenuDao menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    public RestaurantTo get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + " not found."));

        return makeTO(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public RestaurantTo create(Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        return makeTO(created);
    }

    private RestaurantTo makeTO(Restaurant restaurant) {
        RestaurantTo rTo = new RestaurantTo(restaurant);
        rTo.setMenuOfDay(menuRepository.findFirstByRestaurantIdOrderByDateDesc(restaurant.getId()));

        return rTo;
    }
}
