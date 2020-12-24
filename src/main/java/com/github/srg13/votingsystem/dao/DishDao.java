package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DishDao extends JpaRepository<Dish, Integer> {

}
