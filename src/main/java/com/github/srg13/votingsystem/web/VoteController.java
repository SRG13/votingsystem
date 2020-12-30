package com.github.srg13.votingsystem.web;

import com.github.srg13.votingsystem.AuthorizedUser;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {

    protected static final String REST_URL = "/profile/votes";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get all votes for user {}", authUser.getId());
        return service.getAll(authUser.getId());
    }
}
