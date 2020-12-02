package com.github.srg13.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"},
        name = "unique_menu_id_name_idx")})
public class Dish extends NamedEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "price", nullable = false)
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal price;

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", menu=" + menu +
                ", price=" + price +
                '}';
    }
}
