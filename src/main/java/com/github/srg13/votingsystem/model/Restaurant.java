package com.github.srg13.votingsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "address", nullable = false)
    @NotNull
    private String address;

    public Restaurant(Integer id, String name, String description, String address) {
        super(id, name);
        this.description = description;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", address=" + address +
                '}';
    }
}
