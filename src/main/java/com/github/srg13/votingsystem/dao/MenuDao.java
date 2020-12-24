package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuDao extends JpaRepository<Menu, Integer> {

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.id=?1")
    Optional<Menu> findByIdWithDishes(int menuId);

    Optional<Menu> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    List<Menu> findAllByRestaurantId(int restaurantId);
}
