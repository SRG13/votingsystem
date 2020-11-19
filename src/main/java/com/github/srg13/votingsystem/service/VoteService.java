package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.VoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteDao repository;

    @Autowired
    public VoteService(VoteDao repository) {
        this.repository = repository;
    }
}
