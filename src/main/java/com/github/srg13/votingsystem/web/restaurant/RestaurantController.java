package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.model.Restaurant;
import com.github.srg13.votingsystem.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL)
public class RestaurantController {

    protected static final String REST_URL = "/restaurants";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);

        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
