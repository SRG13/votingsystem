package com.github.srg13.votingsystem.model;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Dish extends NamedEntity {

    private BigDecimal price;
}
