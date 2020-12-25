package com.github.srg13.votingsystem.dto;

import com.github.srg13.votingsystem.model.Menu;
import com.github.srg13.votingsystem.model.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RestaurantTo extends AbstractBaseTo {

    private String name;
    private String description;
    private String address;

    private Menu menuOfDay;

    public RestaurantTo(Integer id, String name, String description, String address, Menu menuOfDay) {
        super(id);
        this.name = name;
        this.description = description;
        this.address = address;
        this.menuOfDay = menuOfDay;
    }

    public RestaurantTo(Restaurant restaurant) {
        super(restaurant.getId());
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.address = restaurant.getAddress();
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", address=" + address +
                '}';
    }
}
