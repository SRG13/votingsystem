package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantDao repository;

    @Autowired
    public RestaurantService(RestaurantDao repository) {
        this.repository = repository;
    }
}
