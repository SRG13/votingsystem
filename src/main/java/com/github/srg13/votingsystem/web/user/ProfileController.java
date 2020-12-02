package com.github.srg13.votingsystem.web.user;

import com.github.srg13.votingsystem.AuthorizedUser;
import com.github.srg13.votingsystem.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(ProfileController.REST_URL)
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/profile";

    @GetMapping
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(authUser.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(authUser.getId());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authUser, @RequestBody User user) {
        super.update(user, authUser.getId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User created = super.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .build()
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/vote-history")
    public User getWithVotes(@AuthenticationPrincipal AuthorizedUser authUser) {
        return super.getWithVotes(authUser.getId());
    }
}