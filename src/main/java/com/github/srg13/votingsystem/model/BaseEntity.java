package com.github.srg13.votingsystem.model;

import lombok.Getter;
import lombok.Setter;

public class BaseEntity {

    @Getter
    @Setter
    private Integer id;

    public boolean isNew() {
        return getId() == null;
    }
}
