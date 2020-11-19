package com.github.srg13.votingsystem.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Menu extends NamedEntity {

    private LocalDate date;

    private List<Dish> dishes;

}
