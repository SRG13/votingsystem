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
    @Query("SELECT m FROM Menu m WHERE m.id=:id and m.restaurant.id=:restaurantId")
    Optional<Menu> findByIdAndRestaurantIdWithDishes(int id, int restaurantId);

    Optional<Menu> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    Optional<Menu> findByIdAndRestaurantId(int id, int restaurantId);

    List<Menu> findAllByRestaurantId(int restaurantId);

    boolean existsByIdAndDate(int id, LocalDate date);
}
