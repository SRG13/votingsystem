package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DishDao extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByMenuId(int menuId);
}
