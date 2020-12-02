package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDao extends JpaRepository<Restaurant, Integer> {

}
