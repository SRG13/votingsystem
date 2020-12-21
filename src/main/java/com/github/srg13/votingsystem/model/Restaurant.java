package com.github.srg13.votingsystem.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@NoArgsConstructor
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
