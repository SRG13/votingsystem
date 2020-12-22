package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.srg13.votingsystem.util.ValidationUtil.checkNew;

@Service
public class RestaurantService {

    private final RestaurantDao repository;

    @Autowired
    public RestaurantService(RestaurantDao repository) {
        this.repository = repository;
    }

    public Restaurant get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + "not found."));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return repository.save(restaurant);
    }

}
