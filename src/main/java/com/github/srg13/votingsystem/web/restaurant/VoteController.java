package com.github.srg13.votingsystem.web.restaurant;

import com.github.srg13.votingsystem.AuthorizedUser;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {

    final static String REST_URL = "restaurants/{restaurantId}/menus/{menuId}/vote";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Vote vote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable int menuId) {
        log.info("create vote for user {} and menu {}", authUser.getId(), menuId);
        return service.create(new Vote(), authUser.getId(), menuId);
    }

}
