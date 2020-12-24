package com.github.srg13.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"},
        name = "unique_menu_id_name_idx")})
public class Dish extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Menu menu;

    @Column(name = "price", nullable = false)
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal price;

    public Dish(Integer id, String name, @NotNull @DecimalMin(value = "0.0") @Digits(integer = 4, fraction = 2) BigDecimal price) {
        super(id, name);
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
