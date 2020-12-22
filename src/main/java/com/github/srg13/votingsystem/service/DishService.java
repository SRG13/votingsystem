package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.DishDao;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.srg13.votingsystem.util.ValidationUtil.checkNew;

@Service
public class DishService {

    private final DishDao dishRepository;

    private final MenuService menuService;

    @Autowired
    public DishService(DishDao dishRepository, MenuService menuService) {
        this.dishRepository = dishRepository;
        this.menuService = menuService;
    }

    public Dish get(int id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dish with id=" + id + "not found."));
    }

    @Transactional
    public void delete(int id) {
        dishRepository.deleteById(id);
    }

    public List<Dish> getAll(int menuId) {
        return dishRepository.findAllByMenuId(menuId);
    }

    @Transactional
    public Dish create(Dish dish, int menuId) {
        checkNew(dish);
        dish.setMenu(menuService.get(menuId));

        return dishRepository.save(dish);
    }
}
