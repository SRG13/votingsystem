package com.github.srg13.votingsystem.dto;

import com.github.srg13.votingsystem.model.AbstractNamedEntity;
import com.github.srg13.votingsystem.model.Menu;
import com.github.srg13.votingsystem.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantTo extends AbstractNamedEntity {

    private String description;

    private Menu menuOfDay;

    public RestaurantTo(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
        this.description = restaurant.getDescription();
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                '}';
    }
}
