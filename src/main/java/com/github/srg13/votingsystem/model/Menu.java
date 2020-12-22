package com.github.srg13.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "restaurant_id"},
        name = "menus_unique_date_restaurant_id_idx")})
public class Menu extends AbstractNamedEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "menu_date")
    @NotNull
    private LocalDate date = LocalDate.now();

    public Menu(Integer id, String name, @NotNull LocalDate date) {
        super(id, name);
        this.date = date;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name=" + name +
                ", date=" + date +
                '}';
    }
}
