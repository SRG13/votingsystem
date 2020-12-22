package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.model.Dish;
import com.github.srg13.votingsystem.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(DishController.REST_URL)
public class DishController {

    protected static final String REST_URL = "/restaurants/{restaurantId}/menus/{menuId}/dishes";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DishService dishService;


    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get {}", id);
        return dishService.get(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        dishService.delete(id);
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("getAll from menu {}", menuId);
        return dishService.getAll(menuId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish, @PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("create {} for menu {} and restaurant {}", dish, menuId, restaurantId);

        Dish created = dishService.create(dish, menuId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menuId, created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
