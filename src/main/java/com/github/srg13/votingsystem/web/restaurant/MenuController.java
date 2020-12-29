package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.AuthorizedUser;
import com.github.srg13.votingsystem.model.Menu;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.service.MenuService;
import com.github.srg13.votingsystem.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping(value = MenuController.REST_URL)
public class MenuController {

    protected static final String REST_URL = "/restaurants/{restaurantId}/menus";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MenuService menuService;

    private final VoteService voteService;

    public MenuController(MenuService menuService, VoteService voteService) {
        this.menuService = menuService;
        this.voteService = voteService;
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get {}", id);
        return menuService.get(id, restaurantId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete {} for restaurant {}", id, restaurantId);
        menuService.delete(id, restaurantId);
    }

    @GetMapping
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll from restaurant {}", restaurantId);
        return menuService.getAll(restaurantId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@Valid @RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("create menu {} for restaurant {}, with {} dishes", menu, restaurantId, menu.getDishes().size());
        Menu created = menuService.create(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable int menuId) {
        log.info("create vote for user {} and menu {}", authUser.getId(), menuId);

        Vote created = voteService.create(new Vote(), authUser.getId(), menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(menuId)
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
