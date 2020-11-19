package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.DishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishService {

    private final DishDao repository;

    @Autowired
    public DishService(DishDao repository) {
        this.repository = repository;
    }

}
