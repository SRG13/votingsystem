package com.github.srg13.votingsystem.web;

import com.github.srg13.votingsystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    private final MenuService service;

    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }

    public void create() {
    }

    public void getAll(){
    }
}
