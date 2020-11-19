package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final MenuDao repository;

    @Autowired
    public MenuService(MenuDao repository) {
        this.repository = repository;
    }
}
