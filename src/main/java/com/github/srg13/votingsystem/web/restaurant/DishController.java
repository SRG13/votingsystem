package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.dao.DishDao;
import com.github.srg13.votingsystem.dao.MenuDao;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.github.srg13.votingsystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(DishController.REST_URL)
public class DishController {

    static final String REST_URL = "restaurants/{restaurantId}/menus/{menuId}/dishes";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DishDao repository;

    private final MenuDao menuRepository;


    @Autowired
    public DishController(DishDao repository, MenuDao menuRepository) {
        this.repository = repository;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dish with id=" + id + "not found."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("getAll from menu {}", menuId);
        return repository.findAllByMenuId(menuId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody Dish dish, @PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("create {} for menu {} and restaurant {}", dish, menuId, restaurantId);
        checkNew(dish);

        dish.setMenu(menuRepository.getOne(menuId));
        Dish created = repository.save(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menuId, created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
