package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuDao extends JpaRepository<Menu, Integer> {

    Optional<Menu> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    List<Menu> findAllByRestaurantId(int restaurantId);
}
