package com.github.srg13.votingsystem.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "restaurant_id"},
        name = "menus_unique_date_restaurant_id_idx")})
public class Menu extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "menu_date", nullable = false)
    @NotNull
    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    private List<Dish> dishes;

}
